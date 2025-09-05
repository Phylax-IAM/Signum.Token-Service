package phylax.iam.Signum.Token_Service.common.constant;


/**
 * Enum representing the different types of secret keys used within the system.
 * <p>
 * Each constant corresponds to a specific purpose of a secret key:
 * </p>
 * <ul>
 *     <li>{@link #AUTH_SECRET_KEY} - Key used for authentication tokens.</li>
 *     <li>{@link #REFRESH_SECRET_KEY} - Key used for refresh tokens.</li>
 *     <li>{@link #TEMP_SECRET_KEY} - Temporary key, typically short-lived or one-time use.</li>
 *     <li>{@link #CIPHER_SECRET_KEY} - Key used for encryption/decryption operations.</li>
 * </ul>
 */
public enum SecretKeyTypeConstant {

    /** Key used for authentication tokens. */
    AUTH_SECRET_KEY,

    /** Key used for refresh tokens. */
    REFRESH_SECRET_KEY,

    /** Temporary key, typically short-lived or one-time use. */
    TEMP_SECRET_KEY,

    /** Key used for encryption/decryption operations. */
    CIPHER_SECRET_KEY

}
