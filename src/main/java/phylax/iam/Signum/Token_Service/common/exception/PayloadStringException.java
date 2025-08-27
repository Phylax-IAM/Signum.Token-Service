package phylax.iam.Signum.Token_Service.common.exception;

public class PayloadStringException extends RuntimeException {

    public PayloadStringException() {
      super("Can not process the payload string");
    }

    public PayloadStringException(String message) {
        super(message);
    }
}
