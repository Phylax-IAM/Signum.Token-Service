package phylax.iam.Signum.Token_Service.common.exception;

/**
 * Exception thrown to indicate that a code length value is invalid.
 * <p>
 * This exception is specifically used when a code length falls outside
 * the supported range of {@code (0, 128]}. For example, a value less than
 * or equal to {@code 0}, or greater than {@code 128}, will trigger this exception.
 * </p>
 *
 * <p>
 * This is a runtime exception and does not need to be declared
 * in a method's {@code throws} clause.
 * </p>
 */
public class IllegalCodeLengthException extends RuntimeException {

  /**
   * Constructs a new {@code IllegalCodeLengthException} with a default message
   * indicating that the code length is outside the valid range.
   */
  public IllegalCodeLengthException() {
    super("The codeLength is outside the range: (0, 128]");
  }

  /**
   * Constructs a new {@code IllegalCodeLengthException} with the specified detail message.
   *
   * @param message the detail message explaining the cause of the exception
   */
  public IllegalCodeLengthException(String message) {
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

