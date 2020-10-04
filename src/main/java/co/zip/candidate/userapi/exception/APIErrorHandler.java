package co.zip.candidate.userapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEligibleException.class)
    protected ResponseEntity<Object> handleInEligibility(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "User's financial state is not eligible for account registration.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(NonUniqueException.class)
    protected ResponseEntity<Object> handleNonUniqueEmail (
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Email already existed.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

}
