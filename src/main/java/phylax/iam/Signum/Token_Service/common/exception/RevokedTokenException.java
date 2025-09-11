package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that a revoked token was used or
 * attempted to be validated.
 *
 * <p>
 * This exception is typically raised during authentication or
 * authorization flows when a token that has been explicitly
 * revoked (e.g., due to logout, rotation, or compromise) is
 * encountered. Using such tokens is prohibited and signals
 * an invalid security state.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * if (revokedTokenRepository.existsById(tokenId)) {
 *     throw new RevokedTokenException();
 * }
 * }</pre>
 *
 * <p>
 * Being an unchecked exception (subclass of {@link RuntimeException}),
 * it represents an invalid or unauthorized operation rather than a
 * recoverable condition.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public class RevokedTokenException extends RuntimeException {

    /**
     * Constructs a new {@code RevokedTokenException} with a default
     * message indicating that a revoked token was used.
     */
    public RevokedTokenException() {
        super("Tried to use or validate a revoked token");
    }

    /**
     * Constructs a new {@code RevokedTokenException} with the
     * specified detail message.
     *
     * @param message the detail message describing the error
     */
    public RevokedTokenException(String message) {
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

