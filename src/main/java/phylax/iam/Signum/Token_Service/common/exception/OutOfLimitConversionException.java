package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that a duration or time-based value
 * could not be safely converted because it exceeds the allowable
 * range of {@link Long#MAX_VALUE}.
 *
 * <p>
 * This exception is typically used in scenarios where a conversion
 * from seconds, milliseconds, or another time unit into a {@code long}
 * would overflow, resulting in invalid or undefined behavior.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * long nanos = duration.toNanos();
 * if (nanos > Long.MAX_VALUE) {
 *     throw new OutOfLimitConversionException();
 * }
 * }</pre>
 *
 * <p>
 * Being an unchecked exception (subclass of {@link RuntimeException}),
 * it signals a programming or data validation error rather than
 * a recoverable condition.
 * </p>
 *
 * @author
 *     Pragyanshu Rai
 * @since 1.0
 */
public class OutOfLimitConversionException extends RuntimeException {

    /**
     * Constructs a new {@code OutOfLimitConversionException} with a
     * default detail message indicating that the conversion would
     * exceed {@link Long#MAX_VALUE}.
     */
    public OutOfLimitConversionException() {
        super("The current duration cannot be converted as it will go beyond the range of " + Long.MAX_VALUE);
    }

    /**
     * Constructs a new {@code OutOfLimitConversionException} with the
     * specified detail message.
     *
     * @param message the detail message describing the cause of the error
     */
    public OutOfLimitConversionException(String message) {
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

