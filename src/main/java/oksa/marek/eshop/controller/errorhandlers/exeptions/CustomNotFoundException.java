package oksa.marek.eshop.controller.errorhandlers.exeptions;

public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String fieldName, String fieldValue, Class c) {
        super(c.getSimpleName() + " with " + fieldName + " " + fieldValue + " not found");
    }

}
