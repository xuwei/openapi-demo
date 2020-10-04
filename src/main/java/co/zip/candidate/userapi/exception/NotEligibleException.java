package co.zip.candidate.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEligibleException extends RuntimeException {
    public NotEligibleException() {
        super();
    }
}
