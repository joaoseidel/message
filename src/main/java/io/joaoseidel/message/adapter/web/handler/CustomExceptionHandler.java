package io.joaoseidel.message.adapter.web.handler;

import io.joaoseidel.message.application.exceptions.InvalidScheduleDateException;
import io.joaoseidel.message.application.exceptions.MessageAlreadySentException;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(InvalidScheduleDateException.class)
  public ResponseEntity<ErrorMessage> handleInvalidScheduleDateException(
      InvalidScheduleDateException exception) {
    return ResponseEntity.badRequest()
        .body(ErrorMessage.builder().message(exception.getMessage()).build());
  }

  @ExceptionHandler(MessageNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleMessageNotFoundException(
      MessageNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorMessage.builder().message(exception.getMessage()).build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleConstraintViolationException(
      ConstraintViolationException exception) {
    return ResponseEntity.badRequest()
        .body(ErrorMessage.builder().message(exception.getMessage()).build());
  }

  @ExceptionHandler(MessageAlreadySentException.class)
  public ResponseEntity<ErrorMessage> handleMessageAlreadySentException(
      MessageAlreadySentException exception) {
    return ResponseEntity.badRequest()
        .body(ErrorMessage.builder().message(exception.getMessage()).build());
  }

  @Builder
  @Getter
  static class ErrorMessage {
    private final String message;
  }
}
