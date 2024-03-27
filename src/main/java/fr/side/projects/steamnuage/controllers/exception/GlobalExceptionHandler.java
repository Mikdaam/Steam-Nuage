package fr.side.projects.steamnuage.controllers.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
    var status = HttpStatus.NOT_FOUND;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
    var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  //Check validations if you add validation rules on DTO class
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    var errorMessages = ex.getBindingResult().getFieldErrors()
        .stream().map(FieldError::getDefaultMessage).toList();
    var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), errorMessages.toString()));
  }


  //Check validations if you add validation rules on entity class
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    var errorMessages = new ArrayList<>();
    for (var violation : ex.getConstraintViolations()) {
      errorMessages.add(violation.getMessage());
    }
    var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), errorMessages.toString()));
  }

  //When given wrong request param
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    var status = HttpStatus.METHOD_NOT_ALLOWED;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  //When given invalid request method
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    var status = HttpStatus.METHOD_NOT_ALLOWED;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  //When given invalid values to request body
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    var status = HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    var status = HttpStatus.NOT_FOUND;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getRequestURL()));
  }

  //Invalid media types
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
    var status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    return ResponseEntity.status(status).body(
        new ErrorResponse(status.value(), Objects.requireNonNull(ex.getContentType()).toString())
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleCommonException(Exception ex) {
    var status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
  }
}
