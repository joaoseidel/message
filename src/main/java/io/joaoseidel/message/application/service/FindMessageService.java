package io.joaoseidel.message.application.service;

import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.in.FindMessageUseCase;
import io.joaoseidel.message.application.port.out.FindMessageByIdPort;
import io.joaoseidel.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FindMessageService implements FindMessageUseCase {

  private final FindMessageByIdPort findMessagePort;

  @Override
  public Message findMessage(FindMessageCommand command) throws MessageNotFoundException {
    return findMessagePort.findMessageById(command.getCode());
  }
}
