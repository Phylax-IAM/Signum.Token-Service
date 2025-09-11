package phylax.iam.Signum.Token_Service.service.grpc;

import org.slf4j.Logger;
import phylax.iam.Signum.Token_Service.common.util.time.DurationUtil;
import phylax.iam.Signum.Token_Service.config.app.SecretKeyConfig;
import phylax.iam.signum.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import phylax.iam.Signum.Token_Service.common.security.AESGCM;
import phylax.iam.Signum.Token_Service.common.util.id.UUIDUtil;
import phylax.iam.Signum.Token_Service.common.util.secret.CodeUtil;
import phylax.iam.Signum.Token_Service.common.util.token.PayloadUtil;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import phylax.iam.Signum.Token_Service.dto.payload.TempTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.common.security.SecretKeyGenerator;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.util.token.TokenGeneratorUtil;
import phylax.iam.Signum.Token_Service.common.util.token.TokenValidatorUtil;
import phylax.iam.Signum.Token_Service.common.exception.InvalidJWTException;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.exception.InternalServerException;

import java.util.UUID;
import javax.crypto.SecretKey;


/**
 * Service class responsible for generating and validating temporary tokens
 * used within the authentication and authorization system.
 *
 * <p>
 * A temporary token consists of:
 * <ul>
 *     <li>A subject identifier (UUID v7)</li>
 *     <li>A one-time secure code (numeric, alphabetical, or alphanumeric)</li>
 *     <li>An encrypted payload bound to a limited lifetime</li>
 * </ul>
 * </p>
 *
 * <p>
 * This service handles:
 * <ol>
 *     <li>Generating a temporary token with an encrypted payload</li>
 *     <li>Validating both the token and its associated one-time code</li>
 * </ol>
 * </p>
 *
 * <p>
 * Security is ensured using AES-GCM encryption and dynamic secret key
 * management through {@link SecretKeyGenerator}.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
@Service
public class TempTokenService {

    /**
     * Utility for securely fetching or generating cryptographic keys.
     */
    @Autowired
    private SecretKeyGenerator secretKeyGenerator;

    /**
     * Centralized configuration for managing secret keys.
     */
    @Autowired
    private SecretKeyConfig secretKeyConfig;

    /**
     * The symmetric key used for encrypting and decrypting temporary token payloads.
     */
    private SecretKey encryptKey;

    /**
     * Logger instance for capturing debug and error information.
     */
    private final Logger logger = LoggerFactory.getLogger(TempTokenService.class);

    /**
     * The token class constant for temporary tokens.
     */
    private static final TokenClassConstant tempTokenClass = TokenClassConstant.TEMPORARY;


    public TempTokenService() {
        encryptKey = secretKeyConfig.get(SecretKeyTypeConstant.CIPHER_SECRET_KEY);
    }

    /**
     * Generates a temporary token with an encrypted payload and associated one-time code.
     *
     * <p>Steps:</p>
     * <ol>
     *     <li>Verifies the subject UUID</li>
     *     <li>Generates a one-time code</li>
     *     <li>Builds the token payload DTO</li>
     *     <li>Encrypts the payload using AES-GCM</li>
     *     <li>Generates the final signed token with an expiry</li>
     * </ol>
     *
     * @param request the {@link TempTokenRequest} containing subject and code specifications
     * @return a {@link TempTokenResponse} containing the generated temporary token
     * @throws InternalServerException if encryption or key generation fails
     */
    public TempTokenResponse generateTempToken(TempTokenRequest request) {
        TempTokenResponse.Builder responseBuilder = TempTokenResponse.newBuilder();

        // verify and extract the UUID subject
        UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(request.getSubject(), 7);

        // generate and store the secure code
        final String code = CodeUtil.generateCodeByType(request.getCodeClass(), request.getCodeLength());

        LoggerUtil.logDebug(logger, "Secret Code - " + code);

        // create the payload
        final TempTokenPayloadDTO tempTokenPayloadDTO = TempTokenPayloadDTO
                .builder()
                .subject(subject)
                .oneTimeCode(code)
                .tokenClassConstant(tempTokenClass)
                .build();

        final String tokenPayloadString = PayloadUtil.generateTempPayload(tempTokenPayloadDTO);

        String encryptedPayloadString;

        try {
            encryptedPayloadString = AESGCM.encrypt(tokenPayloadString, encryptKey);
        } catch (Exception e) {
            LoggerUtil.logErrorAndDebug(logger, "", e);
            throw new InternalServerException(e.getMessage());
        }

        final String tempToken = TokenGeneratorUtil.generateToken(
                encryptedPayloadString,
                DurationUtil.getDurationOrDefault(request.getTempTTLSeconds(), tempTokenClass),
                tempTokenClass
        );

        return responseBuilder
                .setTempToken(tempToken)
                .build();
    }

    /**
     * Extracts the encrypted payload from a given token.
     * <p>
     * This method validates the token structure and retrieves
     * the encrypted payload string.
     * </p>
     *
     * @param token The token to validate and extract the payload from.
     * @return The encrypted payload string.
     */
    private String validateAndExtractPayload(String token) {
        return TokenValidatorUtil.extractEncryptedPayload(
                token,
                TokenGeneratorUtil.getPayloadKeyName(),
                TokenGeneratorUtil.getKeyByType(tempTokenClass)
        );
    }

    /**
     * Validates a one-time code by comparing the provided target code
     * with the original stored code.
     *
     * @param targetCode   the code supplied in the validation request
     * @param originalCode the original code stored in the token payload
     * @return {@code true} if both codes match; otherwise {@code false}
     */
    private boolean isValidCode(String targetCode, String originalCode) {
        return originalCode.equals(targetCode);
    }

    /**
     * Validates both a temporary token and its associated one-time code.
     *
     * <p>Steps:</p>
     * <ol>
     *     <li>Decrypts the token to retrieve the payload</li>
     *     <li>Parses and validates the payload</li>
     *     <li>Checks the one-time code for correctness</li>
     * </ol>
     *
     * @param request the {@link ValidateTempTokenRequest} containing token and code to validate
     * @return a {@link ValidateTempTokenResponse} indicating token and code validity
     * @throws InvalidJWTException if the token payload cannot be decrypted
     */
    public ValidateTempTokenResponse validateTempTokenAndCode(ValidateTempTokenRequest request) {
        final ValidateTempTokenResponse.Builder responseBuilder = ValidateTempTokenResponse.newBuilder();

        // decrypt the token
        final String encryptedPayloadString = validateAndExtractPayload(request.getToken());
        String decryptedPayloadString = null;
        try {
            decryptedPayloadString = AESGCM.decrypt(encryptedPayloadString, encryptKey);
        } catch (Exception e) {
            final String message = "Failed to decrypt payload: invalid or corrupted encrypted string.";
            LoggerUtil.logErrorAndDebug(logger, message, e);
            throw new InvalidJWTException(message);
        }

        // validate the token
        final TempTokenPayloadDTO tempTokenPayloadDTO = PayloadUtil.extractTempPayload(decryptedPayloadString);

        // validate the code
        boolean isCodeValid = isValidCode(request.getCode(), tempTokenPayloadDTO.getOneTimeCode());

        return responseBuilder
                .setToken(request.getToken())
                .setCodeIsValid(isCodeValid)
                .setTokenIsValid(true)
                .build();
    }
}
