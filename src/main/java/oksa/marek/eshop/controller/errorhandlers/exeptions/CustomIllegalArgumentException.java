package oksa.marek.eshop.controller.errorhandlers.exeptions;

public class CustomIllegalArgumentException extends RuntimeException {

    public CustomIllegalArgumentException(String message) {
        super(message);
    }
}
