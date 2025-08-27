package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Custom runtime exception to indicate errors during UUID operations.
 * <p>
 * This exception is typically thrown when an illegal or invalid operation
 * related to UUID creation, parsing, or validation occurs.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * if (!isValidUuidFormat(input)) {
 *     throw new UUIDException("Invalid UUID format: " + input);
 * }
 * }</pre>
 *
 * <p>Extends {@link RuntimeException}, so it is unchecked and does not
 * require explicit handling.</p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public class UUIDException extends RuntimeException {

    /**
     * Constructs a new {@code UUIDException} with a default message:
     * {@code "Illegal uuid operation"}.
     */
    public UUIDException() {
        super("Illegal uuid operation");
    }

    /**
     * Constructs a new {@code UUIDException} with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public UUIDException(String message) {
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
