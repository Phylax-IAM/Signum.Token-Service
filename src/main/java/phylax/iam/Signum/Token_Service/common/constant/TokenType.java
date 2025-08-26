package phylax.iam.Signum.Token_Service.common.constant;

/**
 * Enumeration representing the different types of tokens
 * that can be issued and managed within the system.
 *
 * <p>
 * This enum is typically used to distinguish between
 * token formats or generation strategies in the
 * authentication and authorization process.
 * </p>
 *
 * <ul>
 *   <li>{@link #JWT} - JSON Web Token, commonly used for stateless
 *       authentication and token-based access control.</li>
 *   <li>{@link #SHA} - Token generated using a hashing algorithm
 *       (e.g., SHA-based), useful for opaque tokens or
 *       internal system tokens.</li>
 * </ul>
 */
public enum TokenType {

    /**
     * JSON Web Token (JWT).
     * <p>
     * Encoded, self-contained token that carries claims
     * about the user and is cryptographically signed.
     * </p>
     */
    JWT,

    /**
     * SHA-based token.
     * <p>
     * Opaque token generated using a hashing algorithm
     * such as SHA-256. Typically does not expose claims
     * directly in the token payload.
     * </p>
     */
    SHA

}
