package phylax.iam.Signum.Token_Service.common.constant;


/**
 * Enum representing the standard cryptographic algorithms used in the system.
 * <p>
 * Each constant corresponds to a specific algorithm or transformation string
 * used in Java Cryptography APIs such as {@link javax.crypto.Cipher} or {@link javax.crypto.Mac}.
 * </p>
 * <ul>
 *     <li>{@link #DES_ALGO} - Data Encryption Standard (DES) algorithm.</li>
 *     <li>{@link #AES_ALGO} - Advanced Encryption Standard (AES) algorithm.</li>
 *     <li>{@link #HMAC_SHA256} - HMAC using SHA-256 message digest.</li>
 *     <li>{@link #AES_GCM_NOPADDING} - AES in Galois/Counter Mode with no padding.</li>
 * </ul>
 */
public enum SecretAlgorithmConstant {

    /** Data Encryption Standard (DES) algorithm. */
    DES_ALGO("DES"),

    /** Advanced Encryption Standard (AES) algorithm. */
    AES_ALGO("AES"),

    /**
     * The SHA-256 cryptographic hash algorithm.
     * <p>
     * This algorithm produces a 256-bit (32-byte) hash value. It is widely used
     * in security protocols, digital signatures, and integrity verification.
     * </p>
     */
    SHA_256("SHA-256"),

    /** HMAC using SHA-256 message digest. */
    HMAC_SHA256("HmacSHA256"),

    /** AES in Galois/Counter Mode with no padding. */
    AES_GCM_NOPADDING("AES/GCM/NoPadding");

    /** The string representation of the algorithm used in Java crypto APIs. */
    private final String algorithm;

    /**
     * Constructor for the enum constant.
     *
     * @param algorithm the standard algorithm name or transformation string
     */
    SecretAlgorithmConstant(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Returns the string representation of the cryptographic algorithm.
     *
     * @return the algorithm string
     */
    public String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Returns the string representation of this enum constant.
     * By default, this returns the same value as {@link #getAlgorithm()}.
     *
     * @return the algorithm string
     */
    @Override
    public String toString() {
        return this.algorithm;
    }
}
