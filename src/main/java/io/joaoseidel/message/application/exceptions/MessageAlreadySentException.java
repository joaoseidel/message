package io.joaoseidel.message.application.exceptions;

public class MessageAlreadySentException extends Exception {
  public MessageAlreadySentException(String code) {
    super(String.format("The message with ID %s was already sent!", code));
  }
}
