package phylax.iam.Signum.Token_Service.common.util.hashing;

import phylax.iam.Signum.Token_Service.common.constant.SecretAlgorithmConstant;
import phylax.iam.Signum.Token_Service.common.exception.HashingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for hashing operations using secure cryptographic algorithms.
 * <p>
 * This class provides a static API to generate and verify hashes, primarily
 * used for sensitive data such as tokens, credentials, and identifiers.
 * By default, it uses the {@code SHA-256} algorithm as defined in
 * {@link SecretAlgorithmConstant}.
 * </p>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * // Generate a hash
 * String hash = HashingUtil.generateHash("mySecretData");
 *
 * // Verify a hash
 * boolean valid = HashingUtil.verifyHash("mySecretData", hash);
 * }</pre>
 *
 * <p><strong>Note:</strong> This class is immutable and thread-safe.</p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public final class HashingUtil {

    /**
     * A pre-initialized hashing engine configured with the chosen secure algorithm.
     * <p>
     * In this implementation, {@code SHA-256} is used.
     * </p>
     */
    private static final MessageDigest hashingEngine;

    // Static initializer for the hashing engine
    static {
        try {
            hashingEngine = MessageDigest.getInstance(SecretAlgorithmConstant.SHA_256.getAlgorithm());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new HashingException();
        }
    }

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This is a utility class, so it should not be instantiated.
     * </p>
     */
    private HashingUtil() {}

    /**
     * Generates a Base64-encoded cryptographic hash for the given plain text input.
     *
     * @param plainText the input string to be hashed, must not be {@code null}
     * @return the Base64-encoded hash string
     * @throws NullPointerException if {@code plainText} is {@code null}
     */
    public static String generateHash(String plainText) {
        byte[] hashByte = hashingEngine.digest(plainText.getBytes());
        return Base64.getEncoder().encodeToString(hashByte);
    }

    /**
     * Verifies whether the given plain text produces the same hash
     * as the provided target hash.
     *
     * @param plainText  the input string to hash and verify
     * @param targetHash the expected Base64-encoded hash value
     * @return {@code true} if the computed hash matches the target hash,
     *         {@code false} otherwise
     */
    public static boolean verifyHash(String plainText, String targetHash) {
        return targetHash.equals(generateHash(plainText));
    }
}
