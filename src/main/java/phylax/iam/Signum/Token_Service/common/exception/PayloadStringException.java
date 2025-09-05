package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown when a payload string cannot be processed.
 * <p>
 * This typically occurs when the input payload is invalid, malformed, or does not meet
 * the expected format required by the service or utility method.
 * <p>
 * This interceptor extends {@link RuntimeException}, making it an unchecked interceptor.
 */
public class PayloadStringException extends RuntimeException {

    /**
     * Constructs a new {@code PayloadStringException} with a default message.
     * <p>
     * The default message is: "Can not process the payload string".
     */
    public PayloadStringException() {
        super("Can not process the payload string");
    }

    /**
     * Constructs a new {@code PayloadStringException} with the specified detail message.
     *
     * @param message the detail message explaining why the payload string could not be processed
     */
    public PayloadStringException(String message) {
        super(message);
    }

    /**
     * Returns the detail message string of this interceptor.
     *
     * @return the detail message
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Creates a localized description of this interceptor.
     *
     * @return the localized detail message
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    /**
     * Returns a string representation of this interceptor.
     *
     * @return a string containing the class name and the detail message
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

