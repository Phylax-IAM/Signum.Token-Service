package phylax.iam.Signum.Token_Service.service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.exception.InternalServerException;
import phylax.iam.Signum.Token_Service.common.exception.InvalidJWTException;
import phylax.iam.Signum.Token_Service.common.exception.NoSuchTokenException;
import phylax.iam.Signum.Token_Service.common.exception.RevokedTokenException;
import phylax.iam.Signum.Token_Service.common.security.AESGCM;
import phylax.iam.Signum.Token_Service.common.security.SecretKeyGenerator;
import phylax.iam.Signum.Token_Service.common.util.id.UUIDUtil;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import phylax.iam.Signum.Token_Service.common.util.time.DurationUtil;
import phylax.iam.Signum.Token_Service.common.util.token.PayloadUtil;
import phylax.iam.Signum.Token_Service.common.util.token.TokenGeneratorUtil;
import phylax.iam.Signum.Token_Service.common.util.token.TokenValidatorUtil;
import phylax.iam.Signum.Token_Service.config.app.SecretKeyConfig;
import phylax.iam.Signum.Token_Service.dto.payload.AuthTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.dto.payload.RefreshTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.entity.ActiveTokenEntity;
import phylax.iam.Signum.Token_Service.entity.RevokedTokenEntity;
import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.mapper.ActiveToRevokedTokenMapper;
import phylax.iam.Signum.Token_Service.repository.ActiveTokenRepository;
import phylax.iam.Signum.Token_Service.repository.RevokedTokenRepository;
import phylax.iam.signum.*;

import javax.crypto.SecretKey;
import javax.security.auth.Refreshable;
import java.time.Duration;
import java.util.*;


/**
 * Service class responsible for managing the lifecycle of authentication
 * and refresh tokens within the system.
 *
 * <p>This service integrates with the persistence layer to track
 * active and revoked tokens, ensures secure cryptographic operations
 * using {@link SecretKeyConfig}, and provides APIs for generating,
 * validating, refreshing, and revoking tokens.</p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Generate authentication and refresh tokens with secure payloads</li>
 *   <li>Persist active tokens and handle token revocation</li>
 *   <li>Validate tokens, including revocation status and subject binding</li>
 *   <li>Refresh tokens while maintaining TTL (time-to-live) consistency</li>
 * </ul>
 *
 * <h2>Security</h2>
 * <ul>
 *   <li>Payloads are encrypted using AES-GCM with dynamic secret keys</li>
 *   <li>Tokens are signed using HMAC-SHA256 per token class</li>
 *   <li>Revoked tokens are tracked to prevent reuse</li>
 * </ul>
 *
 * @see ActiveTokenEntity
 * @see RevokedTokenEntity
 * @see SecretKeyConfig
 * @see TokenGeneratorUtil
 * @see TokenValidatorUtil
 */
@Service
public class AuthTokenService {

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    @Autowired
    private ActiveTokenRepository activeTokenRepository;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    private static final TokenClassConstant refreshTokenClass = TokenClassConstant.REFRESH;

    private static final TokenClassConstant authTokenClass = TokenClassConstant.AUTHENTICATION;

    private final Logger logger = LoggerFactory.getLogger(AuthTokenService.class);

    /**
     * Revokes all active tokens for a given subject by moving them into the revoked tokens table
     * and then deleting them from the active token table.
     *
     * @param subject the subject UUID for which tokens should be revoked
     * @return {@code true} if revocation and deletion succeed; {@code false} otherwise
     */
    @Transactional
    private boolean moveAndDeleteTokenFor(UUID subject) {
        boolean successfulDeletion = true;

        try {
            // fetch active tokens
            long activeTokenCount = activeTokenRepository.countBySubject(subject);

            // if no active token exists for this subject return true
            if(activeTokenCount > 0) {
                // fetch the tokens
                List<ActiveTokenEntity> activeTokenEntityList = activeTokenRepository
                        .findBySubject(subject);

                // map active to revoked token entity
                List<RevokedTokenEntity> revokedTokenEntityList = ActiveToRevokedTokenMapper
                        .toRevokedTokenEntityList(activeTokenEntityList);

                // save to revoked token table
                revokedTokenRepository.saveAll(revokedTokenEntityList);

                // delete from active token table
                activeTokenRepository.deleteTokensBySubject(subject);
            }

        } catch (Exception e) {
            successfulDeletion = false;
            LoggerUtil.logErrorAndDebug(logger, "Failed to delete the token", e);
        }
        return successfulDeletion;
    }

    /**
     * Validates that the provided authentication and refresh tokens have not been revoked.
     *
     * @param subject the subject UUID tied to the tokens
     * @param authToken the authentication token string
     * @param refreshToken the refresh token string
     * @return {@code true} if neither token is revoked; {@code false} otherwise
     */
    private boolean validateTokenIsNotRevoked(UUID subject, String authToken, String refreshToken) {
        List<String> revokedTokenList = revokedTokenRepository.findRevokedTokenStringBySubject(subject);

        for(String revokedToken : revokedTokenList) {

            if(authToken.equals(revokedToken) || refreshToken.equals(revokedToken)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extracts the encrypted payload from a signed JWT.
     *
     * @param token the JWT token string
     * @param tokenClass the class of the token (authentication or refresh)
     * @return the encrypted payload string
     */
    private String validateAndExtractPayload(String token, TokenClassConstant tokenClass) {
        return TokenValidatorUtil.extractEncryptedPayload(
                token,
                TokenGeneratorUtil.getPayloadKeyName(),
                TokenGeneratorUtil.getKeyByType(tokenClass)
        );
    }

    /**
     * Validates a given authentication and refresh token pair for a specific subject.
     *
     * <p>Checks include:</p>
     * <ul>
     *   <li>Extracting and decrypting payloads</li>
     *   <li>Verifying the auth token exists in persistence</li>
     *   <li>Ensuring the auth token ID matches the refresh token’s reference</li>
     *   <li>Ensuring neither token is revoked</li>
     * </ul>
     *
     * @param request the {@link ValidateTokenRequest} containing auth and refresh tokens
     * @param subject the verified subject UUID
     * @return {@code true} if the token pair is valid; otherwise {@code false}
     */
    private boolean validateToken(ValidateTokenRequest request, UUID subject) {
        boolean isValid = true;
        final String authToken = request.getAuthToken();
        final String refreshToken = request.getRefreshToken();

        try {
            // extract payload from both the tokens
            final String refreshTokenPayloadEncrypted = this.validateAndExtractPayload(
                    refreshToken,
                    refreshTokenClass
            );

            // fetch active token id by subject and token
            final Optional<UUID> authTokenID = activeTokenRepository.findIdBySubjectAndToken(subject, authToken);

            // check if the token is not revoked
            if(this.validateTokenIsNotRevoked(subject, authToken, refreshToken)) {
                throw new RevokedTokenException();
            }

            // decrypt both the payload strings
            final String refreshTokenPayloadString = AESGCM.decrypt(
                    refreshTokenPayloadEncrypted,
                    secretKeyConfig.get(SecretKeyTypeConstant.CIPHER_SECRET_KEY)
            );

            // convert string to payload dto
            final RefreshTokenPayloadDTO refreshTokenPayload = PayloadUtil.extractRefreshPayload(refreshTokenPayloadString);

            // if no such auth token exists in the db
            if(authTokenID.isEmpty()) {
                throw new NoSuchTokenException();
            }

            // if the auth token exists but does not belong to the refresh token
            if(!refreshTokenPayload.getAuthTokenId().equals(authTokenID.get())) {
                throw new InvalidJWTException();
            }

        } catch(Exception e) {
            isValid = false;
            LoggerUtil.logErrorAndDebug(logger, e.getMessage(), e);
        }
        return isValid;
    }

    /**
     * Computes the TTL (time-to-live) duration in seconds for an active token.
     *
     * @param activeTokenEntity the token entity containing issued and expiry timestamps
     * @return the TTL duration in seconds
     */
    private int getTTLDuration(ActiveTokenEntity activeTokenEntity) {
        return (int) Duration.between(activeTokenEntity.getIssuedAt(), activeTokenEntity.getExpiresAt()).getSeconds();
    }

    /**
     * Generates a new pair of authentication and refresh tokens for a subject.
     *
     * <p>If tokens already exist for the subject, they are revoked first.</p>
     *
     * @param request the {@link TokenRequest} containing subject and TTL overrides
     * @return a {@link TokenResponse} containing newly generated tokens
     * @throws InternalServerException if encryption or persistence fails
     */
    @Transactional
    public TokenResponse generateAuthToken(TokenRequest request) {

        // verify and extract uuid
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );

        // check if token already exists and if it does then revoke it
        this.moveAndDeleteTokenFor(subject);

        // create token key
        final ActiveTokenKey authTokenKey = ActiveTokenKey
                .builder()
                .subject(subject)
                .tokenClassConstant(TokenClassConstant.AUTHENTICATION)
                .build();

        final ActiveTokenKey refreshTokenKey = ActiveTokenKey
                .builder()
                .subject(subject)
                .tokenClassConstant(TokenClassConstant.REFRESH)
                .build();

        // create entities for both token
        final ActiveTokenEntity authTokenEntity = ActiveTokenEntity
                .builder()
                .activeTokenKey(authTokenKey)
                .tokenId(UUIDUtil.generateUUIDv7())
                .build();

        final ActiveTokenEntity refreshTokenEntity = ActiveTokenEntity
                .builder()
                .activeTokenKey(refreshTokenKey)
                .tokenId(UUIDUtil.generateUUIDv7())
                .build();

        // create and encrypt auth and refresh token payload
        String authTokenPayloadString, refreshTokenPayloadString;

        try {
            authTokenPayloadString = AESGCM.encrypt(
                    PayloadUtil.generateAuthPayload(
                            AuthTokenPayloadDTO
                                    .builder()
                                    .subject(subject)
                                    .tokenClassConstant(TokenClassConstant.AUTHENTICATION)
                                    .build()
                    ),
                    secretKeyConfig.get(SecretKeyTypeConstant.AUTH_SECRET_KEY)
            );
            refreshTokenPayloadString = AESGCM.encrypt(
                    PayloadUtil.generateRefreshPayload(
                            RefreshTokenPayloadDTO
                                    .builder()
                                    .authTokenId(authTokenEntity.getTokenId())
                                    .subject(subject)
                                    .tokenClassConstant(TokenClassConstant.REFRESH)
                                    .build()
                    ),
                    secretKeyConfig.get(SecretKeyTypeConstant.REFRESH_SECRET_KEY)
            );
        } catch (Exception e) {
            LoggerUtil.logErrorAndDebug(logger, "", e);
            throw new InternalServerException(e.getMessage());
        }

        // create a new refresh token
        final String refreshToken = TokenGeneratorUtil.generateToken(
                refreshTokenPayloadString,
                DurationUtil.getDurationOrDefault(request.getRefreshTTLSeconds(), refreshTokenClass),
                refreshTokenClass
        );

        // create a new auth token
        final String authToken = TokenGeneratorUtil.generateToken(
                authTokenPayloadString,
                DurationUtil.getDurationOrDefault(request.getAuthTTLSeconds(), authTokenClass),
                authTokenClass
        );

        // add token to the entities
        authTokenEntity.setToken(authToken);
        authTokenEntity.setExpiry(
                DurationUtil.getDurationOrDefault(
                        request.getAuthTTLSeconds(),
                        authTokenClass
                )
        );

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiry(
                DurationUtil.getDurationOrDefault(
                        request.getRefreshTTLSeconds(),
                        refreshTokenClass
                )
        );

        // save both to the db
        activeTokenRepository.saveAll(
                List.of(
                        authTokenEntity,
                        refreshTokenEntity
                )
        );

        // return the response
        return TokenResponse
                .newBuilder()
                .setAuthToken(authToken)
                .setRefreshToken(refreshToken)
                .build();
    }

    /**
     * Refreshes an existing authentication token pair.
     *
     * <p>This method validates the provided tokens, revokes them if valid,
     * and issues a new pair of tokens while preserving the original TTL values.</p>
     *
     * @param request the {@link ValidateTokenRequest} containing existing tokens
     * @return a {@link TokenResponse} containing newly generated tokens
     * @throws InvalidJWTException if validation fails or the tokens are expired/invalid
     */
    @Transactional
    public TokenResponse refreshAuthToken(ValidateTokenRequest request) {

        // validate auth and refresh token
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );
        final boolean isValid = this.validateToken(request, subject);

        // if not valid return a response asking for generating a fresh token
        if(!isValid) {
            throw new InvalidJWTException("This is an invalid token please generate a new token instead");
        }

        // store the auth and refresh token TTL duration
        List<ActiveTokenEntity> activeTokenList = activeTokenRepository.findBySubject(subject);
        final ActiveTokenEntity authTokenEntity = activeTokenList
                .stream()
                .filter(x -> x.getToken().equals(request.getAuthToken()))
                .findFirst()
                .orElseThrow(NoSuchTokenException::new);
        final ActiveTokenEntity refreshTokenEntity = activeTokenList
                .stream()
                .filter(x -> x.getToken().equals(request.getRefreshToken()))
                .findFirst()
                .orElseThrow(NoSuchTokenException::new);
        final int authTTLSeconds = this.getTTLDuration(authTokenEntity);
        final int refreshTTLSeconds = this.getTTLDuration(refreshTokenEntity);

        // if valid revoke both the token
        this.moveAndDeleteTokenFor(subject);

        // generate new token and send the response
        return this.generateAuthToken(
                TokenRequest
                        .newBuilder()
                        .setSubject(request.getSubject())
                        .setAuthTTLSeconds(authTTLSeconds)
                        .setRefreshTTLSeconds(refreshTTLSeconds)
                        .build()
        );
    }

    /**
     * Validates an authentication token pair for a given subject.
     *
     * @param request the {@link ValidateTokenRequest} containing tokens to validate
     * @return a {@link ValidateTokenResponse} indicating validity
     */
    public ValidateTokenResponse validateAuthToken(ValidateTokenRequest request) {
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );
        final boolean isValid = this.validateToken(request, subject);
        return ValidateTokenResponse
                .newBuilder()
                .setIsValid(isValid)
                .build();
    }

    /**
     * Revokes all active tokens for a given subject.
     *
     * @param request the {@link RevokeTokenRequest} containing the subject to revoke
     * @return a {@link RevokeTokenResponse} indicating revocation status
     */
    public RevokeTokenResponse revokeAuthToken(RevokeTokenRequest request) {
        // verify and extract uuid
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );
        boolean isRevoked = this.moveAndDeleteTokenFor(subject);
        return RevokeTokenResponse
                .newBuilder()
                .setIsRevoked(isRevoked)
                .build();
    }
}
