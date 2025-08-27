package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate a failure during token hashing operations.
 * <p>
 * This exception is typically raised when the system is unable to generate
 * or process a secure hash for a token due to invalid inputs, unsupported
 * algorithms, or cryptographic errors.
 * </p>
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * if (hash == null) {
 *     throw new HashingException("Failed to generate hash for token ID: " + tokenId);
 * }
 * }</pre>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public class HashingException extends RuntimeException {

  /**
   * Constructs a new {@code HashingException} with a default detail message
   * indicating that token hashing could not be performed.
   */
  public HashingException() {
    super("Unable to create a hash");
  }

  /**
   * Constructs a new {@code HashingException} with the specified detail message.
   *
   * @param message the detail message describing the specific reason for the failure
   */
  public HashingException(String message) {
    super(message);
  }

  /**
   * Returns the detail message string of this exception.
   *
   * @return the detail message string, or {@code null} if none was provided
   */
  @Override
  public String getMessage() {
    return super.getMessage();
  }

  /**
   * Returns the localized description of this exception.
   * <p>
   * Subclasses may override this method to provide locale-specific messages.
   * </p>
   *
   * @return the localized detail message
   */
  @Override
  public String getLocalizedMessage() {
    return super.getLocalizedMessage();
  }

  /**
   * Returns a string representation of this exception, which includes the
   * exception class name and detail message.
   *
   * @return a string representation of this {@code HashingException}
   */
  @Override
  public String toString() {
    return super.toString();
  }
}
