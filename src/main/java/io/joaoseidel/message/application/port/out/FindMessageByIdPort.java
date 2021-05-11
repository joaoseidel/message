package io.joaoseidel.message.application.port.out;

import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.domain.Message;

public interface FindMessageByIdPort {

  Message findMessageById(String messageId) throws MessageNotFoundException;
}
