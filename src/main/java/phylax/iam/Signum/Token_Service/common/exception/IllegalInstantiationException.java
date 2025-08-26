package phylax.iam.Signum.Token_Service.common.exception;

public class IllegalInstantiationException extends RuntimeException {

    public IllegalInstantiationException() {
        super("This class can not be instantiated.");
    }

    public IllegalInstantiationException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
