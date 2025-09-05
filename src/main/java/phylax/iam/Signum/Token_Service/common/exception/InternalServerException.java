package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception representing a generic <b>Internal Server Error</b> (HTTP 500).
 *
 * <p>
 * This interceptor is typically thrown when an unexpected condition occurs on the server
 * that prevents it from fulfilling the request. It extends {@link RuntimeException},
 * making it an unchecked interceptor.
 * </p>
 *
 * <p>
 * Usage of this interceptor allows encapsulation of server-side failures
 * in a consistent way, while still allowing flexibility by providing
 * a custom error message.
 * </p>
 *
 * <h2>Example:</h2>
 * <pre>{@code
 * if (databaseConnection == null) {
 *     throw new InternalServerException("Database connection failed");
 * }
 * }</pre>
 *
 * @author YourName
 * @see RuntimeException
 */
public class InternalServerException extends RuntimeException {

    /**
     * Constructs a new {@code InternalServerException} with a default message
     * of {@code "Internal Server Error"}.
     */
    public InternalServerException() {
        super("Internal Server Error");
    }

    /**
     * Constructs a new {@code InternalServerException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the interceptor
     */
    public InternalServerException(String message) {
        super(message);
    }

    /**
     * Returns the detail message string of this interceptor.
     *
     * @return the detail message string
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Returns the localized description of this interceptor.
     *
     * @return the localized description of the interceptor
     */
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    /**
     * Returns a string representation of this interceptor, which includes
     * the class name and the detail message.
     *
     * @return a string representation of this {@code InternalServerException}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
