package phylax.iam.Signum.Token_Service.common.util.token;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import javax.crypto.SecretKey;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.security.SecretKeyGenerator;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;

import java.util.Date;
import java.time.Instant;
import java.security.NoSuchAlgorithmException;


/**
 * Utility class for generating and managing JSON Web Tokens (JWTs).
 * <p>
 * This component is responsible for creating authentication, refresh,
 * and temporary tokens with optional payloads. It uses {@link SecretKeyGenerator}
 * to securely fetch or generate cryptographic signing keys and ensures that
 * different token classes ({@link TokenClassConstant}) are signed with
 * their respective keys.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Manages and caches secret keys for authentication, refresh, and temporary tokens.</li>
 *     <li>Generates JWTs with or without payloads.</li>
 *     <li>Signs JWTs using the HMAC-SHA256 algorithm.</li>
 * </ul>
 *
 * <h2>Configuration</h2>
 * The payload claim name can be configured using the property:
 * <pre>{@code
 * token.payloadKeyName=payload
 * }</pre>
 * If not provided, it defaults to {@code payload}.
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * String jwt = tokenUtil.generateToken("user123", 15, TokenClassConstant.AUTHENTICATION);
 * }</pre>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
@Component
public final class TokenUtil {

    /** The claim key name used to embed payload data in the JWT. */
    private final String payloadKeyName;

    /** Secret key used to sign temporary tokens. */
    private final SecretKey tempSecretKey;

    /** Secret key used to sign authentication tokens. */
    private final SecretKey authSecretKey;

    /** Secret key used to sign refresh tokens. */
    private final SecretKey refreshSecretKey;

    /** Logger instance for logging token-related operations. */
    private final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * Constructs a new {@code TokenUtil} instance and initializes signing keys
     * for all supported token types using the configured {@link SecretKeyGenerator}.
     *
     * @param secretKeyGenerator the generator used to fetch or create keys
     * @param payloadKeyName     the claim name for payload data (defaults to "payload" if not set)
     * @throws NoSuchAlgorithmException if the required hashing algorithm is not available
     */
    @Autowired
    public TokenUtil(
            SecretKeyGenerator secretKeyGenerator,
            @Value("${token.payloadKeyName:payload}") String payloadKeyName
    ) throws NoSuchAlgorithmException {

        this.payloadKeyName = payloadKeyName;

        // Fetch keys for different token types
        tempSecretKey = secretKeyGenerator.fetchOrGenerateKey(
                SecretKeyTypeConstant.TEMP_SECRET_KEY,
                256,
                SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
        );

        authSecretKey = secretKeyGenerator.fetchOrGenerateKey(
                SecretKeyTypeConstant.AUTH_SECRET_KEY,
                256,
                SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
        );

        refreshSecretKey = secretKeyGenerator.fetchOrGenerateKey(
                SecretKeyTypeConstant.REFRESH_SECRET_KEY,
                256,
                SecretAlgorithmConstant.HMAC_SHA256.getAlgorithm()
        );
    }

    /**
     * Retrieves the signing key corresponding to the provided token class.
     *
     * @param tokenClassConstant the type of token (authentication, refresh, or temporary)
     * @return the secret key for the given token class
     */
    public SecretKey getKeyByType(TokenClassConstant tokenClassConstant) {
        if (tokenClassConstant == TokenClassConstant.AUTHENTICATION) {
            return authSecretKey;
        } else if (tokenClassConstant == TokenClassConstant.REFRESH) {
            return refreshSecretKey;
        }
        return tempSecretKey;
    }

    /**
     * Generates a JWT with or without a payload, based on the provided arguments.
     *
     * @param tokenPayload       optional payload to embed in the token (if {@code null} or empty, no payload is included)
     * @param minutesToAdd       number of minutes after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT as a {@link String}
     */
    public String generateToken(String tokenPayload, long minutesToAdd, TokenClassConstant tokenClassConstant) {
        if (tokenPayload != null && tokenPayload.length() > 1) {
            return generateTokenWithPayload(tokenPayload, minutesToAdd, tokenClassConstant);
        }
        return generateTokenWithoutPayload(minutesToAdd, tokenClassConstant);
    }

    /**
     * Generates a JWT with the specified payload.
     *
     * @param tokenPayload       the payload to include as a claim
     * @param minutesToAdd       number of minutes after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT containing the payload
     */
    public String generateTokenWithPayload(String tokenPayload, long minutesToAdd, TokenClassConstant tokenClassConstant) {
        return Jwts.builder()
                .claim(payloadKeyName, tokenPayload)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(minutesToAdd)))
                .signWith(this.getKeyByType(tokenClassConstant), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generates a JWT without any payload claims.
     *
     * @param minutesToAdd       number of minutes after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT without payload claims
     */
    public String generateTokenWithoutPayload(long minutesToAdd, TokenClassConstant tokenClassConstant) {
        return Jwts.builder()
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(minutesToAdd)))
                .signWith(this.getKeyByType(tokenClassConstant), Jwts.SIG.HS256)
                .compact();
    }
}