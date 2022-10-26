package cpu.bound.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> highestLevelExceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
