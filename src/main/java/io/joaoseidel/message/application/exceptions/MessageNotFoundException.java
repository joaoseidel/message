package io.joaoseidel.message.application.exceptions;

public class MessageNotFoundException extends Exception {
  public MessageNotFoundException(String messageId) {
    super(String.format("Message with ID '%s' does not exist!", messageId));
  }
}
