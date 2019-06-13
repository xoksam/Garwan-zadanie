package oksa.marek.eshop.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class MyIllegalArgumentException extends RuntimeException {
    public MyIllegalArgumentException(String message) {
        super(message);
    }
}
