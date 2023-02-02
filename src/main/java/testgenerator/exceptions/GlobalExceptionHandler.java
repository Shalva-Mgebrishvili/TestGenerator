package testgenerator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleException(Exception e, HttpServletRequest request) {
        String message = e.getMessage();

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        log.error("Request: {}, threw an exception. Message: {}.", request.getRequestURI(), e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionBody);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        String message = e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("\n"));

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(HttpStatus.BAD_REQUEST.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionBody);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionBody> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {

        String str = e.getMostSpecificCause().getMessage();

        String message = str;

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(HttpStatus.BAD_REQUEST.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionBody);

    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ExceptionBody> handleRestClientResponseException(RestClientResponseException e, HttpServletRequest request) {

        String str = e.getMessage().split("\"error_description\":\"")[1];
        String message = str.split("\"}\"")[0];

        HttpStatus status = HttpStatus.valueOf(e.getRawStatusCode());

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(status.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(status).body(exceptionBody);

    }

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<ExceptionBody> handleHttpResponseException(HttpResponseException e, HttpServletRequest request) {

        String str = e.toString().split("\"error_description\":\"")[1];
        String message = str.split("\"}")[0];

        HttpStatus status = HttpStatus.valueOf(e.getStatusCode());

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(status.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(status).body(exceptionBody);

    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionBody> AuthorizationDeniedException(AuthorizationDeniedException e, HttpServletRequest request) {

        String str = e.getMessage();

        String message = str;

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(status.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(status).body(exceptionBody);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionBody> handleHttpMethodNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {

        String str = e.getMessage();
        String message = str;
        HttpStatus status = HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value());

        ExceptionBody exceptionBody = new ExceptionBody(Integer.toString(status.value()), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(status).body(exceptionBody);
    }

}
