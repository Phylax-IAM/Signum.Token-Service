package phylax.iam.Signum.Token_Service.common.exception;


/**
 * Exception thrown to indicate that an attempt was made to instantiate a class
 * that is not intended to be instantiated.
 * <p>
 * This is commonly used in utility classes or classes with only static members
 * where instantiation is explicitly forbidden. For example:
 * </p>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * public final class UtilityClass {
 *
 *     private UtilityClass() {
 *         throw new IllegalInstantiationException();
 *     }
 *
 *     public static void doSomething() {
 *         // ...
 *     }
 * }
 * }</pre>
 *
 * <p><strong>Note:</strong> This class extends {@link RuntimeException}, so it
 * is unchecked and does not need to be declared in a method's {@code throws} clause.</p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public class IllegalInstantiationException extends RuntimeException {

    /**
     * Constructs a new {@code IllegalInstantiationException} with a default message
     * indicating that the class cannot be instantiated.
     */
    public IllegalInstantiationException() {
        super("This class can not be instantiated.");
    }

    /**
     * Constructs a new {@code IllegalInstantiationException} with the specified detail message.
     *
     * @param message the detail message to include in the exception
     */
    public IllegalInstantiationException(String message) {
        super(message);
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
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}