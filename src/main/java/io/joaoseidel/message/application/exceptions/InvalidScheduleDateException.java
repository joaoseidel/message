package io.joaoseidel.message.application.exceptions;

public class InvalidScheduleDateException extends Exception {
  public InvalidScheduleDateException() {
    super("Message does not have a valid schedule date!");
  }
}
