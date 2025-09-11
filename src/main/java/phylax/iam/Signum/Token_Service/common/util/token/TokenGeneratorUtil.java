package phylax.iam.Signum.Token_Service.common.util.token;

import lombok.Getter;
import org.slf4j.Logger;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.security.SecretKeyGenerator;
import phylax.iam.Signum.Token_Service.common.constant.SecretKeyTypeConstant;
import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;
import phylax.iam.Signum.Token_Service.config.app.SecretKeyConfig;

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
 * // Initialize once at application startup
 * TokenGeneratorUtil.init(secretKeyGenerator, "payload");
 *
 * // Generate a JWT for a subject with 5 minutes expiry
 * String jwt = TokenGeneratorUtil.generateToken("user123", 300, TokenClassConstant.AUTHENTICATION);
 * }</pre>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public final class TokenGeneratorUtil {

    /**
     * The cryptographic key size (in bits) used for generating
     * HMAC-SHA256 signing keys.
     * <p>
     * Default value is {@code 256}, which is the recommended
     * key size for HS256 to ensure adequate security strength.
     * </p>
     */
    @Getter
    private static final int keySize = 256;

    /**
     * The claim key name used to embed payload data in the JWT.
     * <p>
     * Defaults to {@code "payload"} if not configured explicitly via {@link #init(String)}.
     * </p>
     */
    @Getter
    private static String payloadKeyName;

    private static SecretKeyConfig secretKeyConfig;

    /** Logger instance for logging token-related operations and errors. */
    private static final Logger logger = LoggerFactory.getLogger(TokenGeneratorUtil.class);

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * Throws {@link IllegalInstantiationException} if invoked reflectively.
     * </p>
     */
    private TokenGeneratorUtil() {
        throw new IllegalInstantiationException();
    }

    /**
     * Initializes signing keys for all supported token types using the configured {@link SecretKeyGenerator}.
     * <p>
     * Must be called once at application startup before any token operations are performed.
     * </p>
     *
     * @param payloadKeyName     the claim name for payload data (defaults to "payload" if {@code null} or empty)
     */
    public static void init(SecretKeyConfig secretKeyConfig, String payloadKeyName) {
        TokenGeneratorUtil.secretKeyConfig = secretKeyConfig;
        TokenGeneratorUtil.payloadKeyName =
                (payloadKeyName == null || payloadKeyName.isBlank()) ? "payload" : payloadKeyName;
    }

    /**
     * Retrieves the signing key corresponding to the provided token class.
     *
     * @param tokenClassConstant the type of token (authentication, refresh, or temporary)
     * @return the secret key for the given token class
     */
    public static SecretKey getKeyByType(TokenClassConstant tokenClassConstant) {
        if (tokenClassConstant == TokenClassConstant.AUTHENTICATION) {
            return TokenGeneratorUtil.secretKeyConfig.get(SecretKeyTypeConstant.AUTH_SECRET_KEY);
        } else if (tokenClassConstant == TokenClassConstant.REFRESH) {
            return TokenGeneratorUtil.secretKeyConfig.get(SecretKeyTypeConstant.REFRESH_SECRET_KEY);
        }
        return TokenGeneratorUtil.secretKeyConfig.get(SecretKeyTypeConstant.TEMP_SECRET_KEY);
    }

    /**
     * Generates a JWT with or without a payload, depending on the provided arguments.
     *
     * @param tokenPayload       optional payload to embed in the token (if {@code null} or empty, no payload is included)
     * @param secondsToAdd       number of seconds after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT as a {@link String}
     */
    public static String generateToken(String tokenPayload, long secondsToAdd, TokenClassConstant tokenClassConstant) {
        if (tokenPayload != null && tokenPayload.length() > 1) {
            return generateTokenWithPayload(tokenPayload, secondsToAdd, tokenClassConstant);
        }
        return generateTokenWithoutPayload(secondsToAdd, tokenClassConstant);
    }

    /**
     * Generates a JWT with the specified payload claim.
     *
     * @param tokenPayload       the payload to include as a claim
     * @param secondsToAdd       number of seconds after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT containing the payload
     */
    public static String generateTokenWithPayload(String tokenPayload, long secondsToAdd, TokenClassConstant tokenClassConstant) {
        return Jwts.builder()
                .claim(payloadKeyName, tokenPayload)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(secondsToAdd)))
                .signWith(TokenGeneratorUtil.getKeyByType(tokenClassConstant), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generates a JWT without any payload claims.
     *
     * @param secondsToAdd       number of seconds after which the token should expire
     * @param tokenClassConstant the token class determining which secret key is used
     * @return a signed JWT without payload claims
     */
    public static String generateTokenWithoutPayload(long secondsToAdd, TokenClassConstant tokenClassConstant) {
        return Jwts.builder()
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(secondsToAdd)))
                .signWith(TokenGeneratorUtil.getKeyByType(tokenClassConstant), Jwts.SIG.HS256)
                .compact();
    }
}