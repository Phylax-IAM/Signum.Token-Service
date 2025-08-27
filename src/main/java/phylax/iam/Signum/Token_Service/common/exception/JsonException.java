package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception indicating a failure during JSON processing or conversion.
 * <p>
 * This runtime exception is typically thrown when an error occurs while
 * serializing or deserializing JSON objects, or when an invalid JSON
 * string is encountered.
 * </p>
 *
 * <p>Example scenarios include:</p>
 * <ul>
 *   <li>Failure to convert a POJO to JSON string</li>
 *   <li>Failure to parse a JSON string into a POJO</li>
 *   <li>Unexpected or malformed JSON input</li>
 * </ul>
 *
 * <p>This exception extends {@link RuntimeException}, making it an
 * unchecked exception. It can be used to wrap lower-level exceptions
 * from libraries like Jackson or Gson, while keeping the API clean.</p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public class JsonException extends RuntimeException {

    /**
     * Constructs a new {@code JsonException} with a default message
     * indicating an inability to process JSON.
     */
    public JsonException() {
        super("Unable to process this json conversion");
    }

    /**
     * Constructs a new {@code JsonException} with the specified detail message.
     *
     * @param message the detail message to describe the error
     */
    public JsonException(String message) {
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