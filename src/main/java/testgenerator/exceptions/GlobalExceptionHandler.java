package testgenerator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionBody> handleResponseStatusException (ResponseStatusException e, HttpServletRequest request) {
        String message = e.getMessage();
        String status = Integer.valueOf(e.getStatus().value()).toString();

        ExceptionBody exceptionBody = new ExceptionBody(status, message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(e.getStatus()).body(exceptionBody);
    }
}
