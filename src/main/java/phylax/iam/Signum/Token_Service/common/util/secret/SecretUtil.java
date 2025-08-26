package phylax.iam.Signum.Token_Service.common.util.secret;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for encoding and decoding {@link SecretKey} objects
 * to and from Base64-encoded string representations.
 * <p>
 * This is particularly useful when cryptographic keys need to be
 * stored or transferred as text (e.g., in configuration files,
 * environment variables, or over JSON-based APIs).
 * </p>
 * <p>
 * <strong>Note:</strong> Base64 encoding does not provide encryption
 * or security—it only encodes binary data as ASCII text. Ensure that
 * encoded keys are handled securely and protected by appropriate
 * access controls.
 * </p>
 */
public final class SecretUtil {

    /**
     * Private constructor to prevent instantiation.
     * This class is intended for static utility access only.
     */
    private SecretUtil() {}

    /**
     * Reconstructs a {@link SecretKey} from its Base64-encoded string representation.
     *
     * <p>The caller must specify the key algorithm (e.g., {@code "AES"},
     * {@code "HmacSHA256"}) that matches the encoded key.</p>
     *
     * @param base64Key the Base64-encoded key string, must not be {@code null} or empty
     * @param type the key algorithm (e.g., "AES", "HmacSHA256"), must not be {@code null}
     * @return the reconstructed {@link SecretKey}
     * @throws IllegalArgumentException if the input is not a valid Base64 string
     *                                  or if the key cannot be constructed
     */
    public static SecretKey fromBase64String(String base64Key, String type) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, type);
    }

    /**
     * Encodes a {@link SecretKey} into its Base64 string representation.
     *
     * <p>This allows safe storage or transfer of keys as text, for example
     * in environment variables or configuration files. The returned string
     * can later be reconstructed into a {@link SecretKey} using
     * {@link #fromBase64String(String, String)}.</p>
     *
     * @param secretKey the key to encode, must not be {@code null}
     * @return the Base64-encoded key string
     * @throws IllegalArgumentException if the key cannot be encoded
     */
    public static String toBase64String(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
