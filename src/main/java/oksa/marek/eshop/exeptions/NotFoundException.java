package oksa.marek.eshop.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private Long itemID;
    private Class aClass;

    public NotFoundException(Long itemID, Class c) {
        super("Cannot find " + c.getSimpleName() + " with id " + itemID);
        this.itemID = itemID;
        this.aClass = c;
    }
    public NotFoundException(String name, Class c) {
        super("Cannot find " + c.getSimpleName() + " with name " + name);
        this.aClass = c;
    }

    public String getMsg() {
        return "Cannot find " + aClass.getSimpleName() + " with id " + itemID;
    }
}
