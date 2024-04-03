package ecnic.service.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class CustomErrorResponse {
    private String message;
    private String errorCode;
    private ZonedDateTime errorAt;
    private HttpStatus errorStatus;
}
