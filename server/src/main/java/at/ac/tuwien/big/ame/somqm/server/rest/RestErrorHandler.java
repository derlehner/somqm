package at.ac.tuwien.big.ame.somqm.server.rest;

import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

  private final static DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  // Handling application exceptions

  @ExceptionHandler(value = IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e,
      WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = NoSuchElementException.class)
  protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e,
      WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
        HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = ServiceException.class)
  protected ResponseEntity<Object> handleServiceException(ServiceException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  // Handling framework exceptions

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse();
    String timestamp = ZonedDateTime.now(ZoneOffset.UTC).format(FORMATTER);
    errorResponse.setTimestamp(timestamp);
    errorResponse.setStatus(status.value());
    errorResponse.setError(status.getReasonPhrase());
    errorResponse.setMessage(e.getMessage());
    String path = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
    errorResponse.setPath(path);
    return new ResponseEntity<>(errorResponse, headers, status);
  }

  public static class ErrorResponse {

    /**
     * Attention: Getters must be public. Otherwise an exception is thrown during runtime: "No
     * converter found for return value of type: class ....rest.RestErrorHandler$ErrorResponse"
     */

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    private ErrorResponse() {
    }

    public String getTimestamp() {
      return timestamp;
    }

    private void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
    }

    public int getStatus() {
      return status;
    }

    private void setStatus(int status) {
      this.status = status;
    }

    public String getError() {
      return error;
    }

    private void setError(String error) {
      this.error = error;
    }

    public String getMessage() {
      return message;
    }

    private void setMessage(String message) {
      this.message = message;
    }

    public String getPath() {
      return path;
    }

    private void setPath(String path) {
      this.path = path;
    }
  }
}
