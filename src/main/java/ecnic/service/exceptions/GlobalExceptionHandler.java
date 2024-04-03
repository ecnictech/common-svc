package ecnic.service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e) {
        log.error("Handle Custom Exception caught: {}", e.getMessage());
        CustomErrorResponse cer = new CustomErrorResponse();
        cer.setMessage(e.getMessage());
        cer.setErrorStatus(e.getHttpStatus());
        cer.setErrorAt(e.getErrorAt());
        cer.setErrorCode(e.getErrorCode());
        return ResponseEntity.internalServerError().body(cer);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        log.error("Handle Generic Exception caught: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode().value()).body(e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception e) {
        log.error("Handle Generic Exception caught: {}", e.getMessage());
        CustomErrorResponse cer = new CustomErrorResponse();
        cer.setMessage("Something went wrong with the server");
        cer.setErrorStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        cer.setErrorAt(ZonedDateTime.now());
        cer.setErrorCode("GENERIC");
        return ResponseEntity.internalServerError().body(cer);
    }

}
