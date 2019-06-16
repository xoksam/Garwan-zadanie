package oksa.marek.eshop.controller.errorhandlers;

import oksa.marek.eshop.controller.errorhandlers.exeptions.CustomIllegalArgumentException;
import oksa.marek.eshop.controller.errorhandlers.exeptions.CustomNotFoundException;
import oksa.marek.eshop.controller.errorhandlers.responses.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotAcceptable(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.value(),
                ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}
