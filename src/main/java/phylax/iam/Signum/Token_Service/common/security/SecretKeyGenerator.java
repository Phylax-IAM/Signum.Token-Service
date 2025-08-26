package phylax.iam.Signum.Token_Service.common.security;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for generating, encoding, and decoding {@link SecretKey} instances.
 *
 * <p>This class simplifies the management of symmetric keys by providing methods to:</p>
 * <ul>
 *   <li>Generate new cryptographic keys of a specified size and type.</li>
 *   <li>Convert keys to and from Base64 strings for storage or transmission.</li>
 *   <li>Fetch an existing key from environment variables, or generate a new one if none is found.</li>
 * </ul>
 *
 * <p><strong>Security Note:</strong> Keys must be handled with care.
 * For production systems, consider secure key storage solutions (e.g.,
 * Java KeyStore, AWS KMS, HashiCorp Vault) instead of environment variables.</p>
 *
 * @see javax.crypto.SecretKey
 * @see javax.crypto.KeyGenerator
 * @see javax.crypto.spec.SecretKeySpec
 */
public final class SecretKeyGenerator {

    /**
     * Reconstructs a {@link SecretKey} from its Base64-encoded string representation.
     *
     * @param base64Key the Base64-encoded key string
     * @param type the key algorithm (e.g., "AES", "HmacSHA256")
     * @return the reconstructed {@link SecretKey}
     */
    public static SecretKey fromBase64String(String base64Key, String type) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, type);
    }

    /**
     * Encodes a {@link SecretKey} into a Base64 string.
     *
     * <p>This allows safe storage or transfer of keys as text.</p>
     *
     * @param secretKey the key to encode
     * @return the Base64-encoded key string
     */
    public static String toBase64String(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Generates a new cryptographic {@link SecretKey}.
     *
     * @param keySize the key size in bits (e.g., 128, 192, 256 for AES)
     * @param type the key algorithm (e.g., "AES", "HmacSHA256")
     * @return the newly generated {@link SecretKey}
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public static SecretKey generateKey(int keySize, String type) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    /**
     * Fetches a {@link SecretKey} from an environment variable, or generates a new one if not found.
     *
     * <p>If the environment variable with the given {@code secretKeyName} exists,
     * its value is assumed to be a Base64-encoded key and is reconstructed.
     * Otherwise, a new key is generated.</p>
     *
     * @param secretKeyName the name of the environment variable containing the Base64 key
     * @param keySize the key size in bits (used if a new key is generated)
     * @param type the key algorithm (e.g., "AES", "HmacSHA256")
     * @return the fetched or newly generated {@link SecretKey}
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public static SecretKey fetchOrGenerateKey(String secretKeyName, int keySize, String type) throws NoSuchAlgorithmException {
        String secretKeyString = System.getenv(secretKeyName);
        SecretKey secretKey;

        if(secretKeyString == null) {
            secretKey = generateKey(keySize, type);
        } else {
            secretKey = fromBase64String(secretKeyString, type);
        }
        return secretKey;
    }
}
