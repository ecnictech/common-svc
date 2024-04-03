package ecnic.service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime errorAt;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorAt = ZonedDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomException(String errorCode, Exception e) {
        super(e.getMessage());
        this.errorCode = errorCode;
        this.errorAt = ZonedDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorAt = ZonedDateTime.now();
        this.httpStatus = httpStatus;
    }

}
