package phylax.iam.Signum.Token_Service.common.exception;

public class InvalidJWTException extends RuntimeException {

    public InvalidJWTException() {
        super("This is an invalid Json Web Token");
    }

    public InvalidJWTException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
