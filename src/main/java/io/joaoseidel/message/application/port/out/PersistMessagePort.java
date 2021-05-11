package io.joaoseidel.message.application.port.out;

import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.domain.Message;

public interface PersistMessagePort {

  Message scheduleMessage(Message message);

  void deleteMessage(String code) throws MessageNotFoundException;
}
