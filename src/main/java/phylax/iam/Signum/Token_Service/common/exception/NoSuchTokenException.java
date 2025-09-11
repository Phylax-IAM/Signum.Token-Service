package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that a requested token does not exist.
 *
 * <p>
 * This exception is typically raised when a token lookup fails in
 * persistence (e.g., database, cache) or when a client attempts to
 * use a token identifier that is unknown to the system. It signals
 * that the token is invalid because it has either expired, been
 * deleted, or never existed in the first place.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * UserActiveTokenEntity token = tokenRepository.findById(tokenId)
 *     .orElseThrow(NoSuchTokenException::new);
 * }</pre>
 *
 * <p>
 * Being an unchecked exception (subclass of {@link RuntimeException}),
 * it indicates a logical error or invalid client request rather than
 * a recoverable condition.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public class NoSuchTokenException extends RuntimeException {

    /**
     * Constructs a new {@code NoSuchTokenException} with a default
     * message indicating that no such token exists.
     */
    public NoSuchTokenException() {
        super("No such token exists");
    }

    /**
     * Constructs a new {@code NoSuchTokenException} with the
     * specified detail message.
     *
     * @param message the detail message describing the missing token
     */
    public NoSuchTokenException(String message) {
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
