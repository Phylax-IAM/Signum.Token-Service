package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that a requested cryptographic secret key
 * could not be found or does not exist in the configured key store or
 * persistence layer.
 *
 * <p>
 * This is an unchecked exception extending {@link RuntimeException},
 * and is typically used in scenarios where:
 * <ul>
 *   <li>A key lookup fails in {@code SecretKeyGenerator} or related utilities.</li>
 *   <li>An invalid or missing key identifier is provided.</li>
 *   <li>A key has been revoked, deleted, or was never generated.</li>
 * </ul>
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * SecretKey key = secretKeyRepository.findById(keyId)
 *     .orElseThrow(NoSuchSecretKeyException::new);
 * }</pre>
 *
 * <p>
 * This exception is intended for signaling programming or configuration
 * errors, and as such, is unchecked.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public class NoSuchSecretKeyException extends RuntimeException {

    /**
     * Constructs a new {@code NoSuchSecretKeyException} with a default
     * detail message of {@code "No Such Secret Key Exists"}.
     */
    public NoSuchSecretKeyException() {
        super("No Such Secret Key Exists");
    }

    /**
     * Constructs a new {@code NoSuchSecretKeyException} with the
     * specified detail message.
     *
     * @param message the detail message describing the error
     */
    public NoSuchSecretKeyException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

