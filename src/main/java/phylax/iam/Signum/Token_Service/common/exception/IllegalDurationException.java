package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that an invalid or unsupported duration
 * value has been provided.
 *
 * <p>
 * This exception is typically raised when a duration falls outside
 * the expected range of {@code 0} to {@link Integer#MAX_VALUE},
 * which represents the maximum safe duration (in seconds or another
 * unit depending on the context) supported by the system.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * if (ttlSeconds < 0 || ttlSeconds > Integer.MAX_VALUE) {
 *     throw new IllegalDurationException();
 * }
 * }</pre>
 *
 * <p>
 * Being an unchecked exception (subclass of {@link RuntimeException}),
 * it is intended to signal invalid input or programming errors
 * rather than recoverable conditions.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public class IllegalDurationException extends RuntimeException {

    /**
     * Constructs a new {@code IllegalDurationException} with a default
     * detail message indicating that the duration must be within
     * {@code 0} and {@link Integer#MAX_VALUE}.
     */
    public IllegalDurationException() {
        super(String.format(
                "Illegal Duration provided as it should be within the range - %d - %d",
                0, Integer.MAX_VALUE
        ));
    }

    /**
     * Constructs a new {@code IllegalDurationException} with the
     * specified detail message.
     *
     * @param message the detail message describing the invalid duration
     */
    public IllegalDurationException(String message) {
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
