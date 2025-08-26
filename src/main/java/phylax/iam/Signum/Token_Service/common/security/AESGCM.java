package phylax.iam.Signum.Token_Service.common.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class providing AES encryption and decryption using
 * <strong>AES-GCM (Galois/Counter Mode)</strong> with no padding.
 *
 * <p>This class is designed for secure symmetric encryption
 * with authentication. It ensures confidentiality and integrity
 * by using AES in GCM mode with a 128-bit authentication tag
 * and a 96-bit (12-byte) random IV.</p>
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Generates a secure random IV for each encryption.</li>
 *   <li>Prepends the IV to the ciphertext for proper decryption.</li>
 *   <li>Encodes the final encrypted output in Base64 for safe storage or transmission.</li>
 * </ul>
 *
 * <p><strong>Note:</strong> This class is stateless and thread-safe.
 * The secret key must be securely generated and managed externally
 * (e.g., via a KeyStore or environment variables).</p>
 *
 * @see javax.crypto.Cipher
 * @see javax.crypto.spec.GCMParameterSpec
 * @see javax.crypto.SecretKey
 */
public final class AESGCM {

    private static final String AES_ALGO = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private AESGCM() {}

    /**
     * Initializes and returns a {@link Cipher} instance configured for AES-GCM
     * encryption or decryption.
     *
     * @param key the secret AES key (must match AES key size, e.g., 128/192/256 bits)
     * @param iv the initialization vector (must be 12 bytes for GCM)
     * @param cipherMode the cipher mode ({@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE})
     * @return the initialized {@link Cipher} instance
     * @throws Exception if the cipher cannot be initialized
     */
    private static Cipher getInitCipher(SecretKey key, byte[] iv, int cipherMode) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(cipherMode, key, gcmParameterSpec);
        return cipher;
    }

    /**
     * Encrypts the given plain text using AES-GCM.
     *
     * <p>A random 12-byte IV is generated for each encryption.
     * The IV is prepended to the ciphertext and the result is
     * encoded as a Base64 string.</p>
     *
     * @param plainText the text to encrypt (UTF-8 encoded)
     * @param key the AES secret key
     * @return the Base64-encoded ciphertext, which includes the IV
     * @throws Exception if encryption fails
     */
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = getInitCipher(key, iv, Cipher.ENCRYPT_MODE);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] encrypted = new byte[IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, encrypted, 0, IV_LENGTH);
        System.arraycopy(cipherText, 0, encrypted, IV_LENGTH, cipherText.length);

        return Base64.getEncoder().encodeToString(encrypted);
    }


    /**
     * Decrypts the given Base64-encoded ciphertext using AES-GCM.
     *
     * <p>The method extracts the IV (first 12 bytes) and uses it to
     * properly initialize the cipher for decryption.</p>
     *
     * @param cipherTextBase64 the Base64-encoded ciphertext (with IV prepended)
     * @param key the AES secret key
     * @return the decrypted plain text (UTF-8 encoded)
     * @throws Exception if decryption fails (e.g., authentication tag mismatch)
     */
    public static String decrypt(String cipherTextBase64, SecretKey key) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(cipherTextBase64);

        byte[] iv = new byte[IV_LENGTH];
        byte[] cipherText = new byte[decoded.length - IV_LENGTH];

        System.arraycopy(decoded, 0, iv, 0, IV_LENGTH);
        System.arraycopy(decoded, IV_LENGTH, cipherText, 0, cipherText.length);

        Cipher cipher = getInitCipher(key, iv, Cipher.DECRYPT_MODE);

        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText, StandardCharsets.UTF_8);
    }
}
