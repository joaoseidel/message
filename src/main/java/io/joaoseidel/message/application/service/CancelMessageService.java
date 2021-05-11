package io.joaoseidel.message.application.service;

import io.joaoseidel.message.application.exceptions.MessageAlreadySentException;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.in.CancelMessageUseCase;
import io.joaoseidel.message.application.port.in.FindMessageUseCase;
import io.joaoseidel.message.application.port.in.FindMessageUseCase.FindMessageCommand;
import io.joaoseidel.message.application.port.out.PersistMessagePort;
import io.joaoseidel.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelMessageService implements CancelMessageUseCase {

  private final FindMessageUseCase findMessageUseCase;
  private final PersistMessagePort persistMessagePort;

  @Override
  public void cancelMessage(CancelMessageCommand command)
      throws MessageNotFoundException, MessageAlreadySentException {
    var findMessageCommand = new FindMessageCommand(command.getCode());
    var message = findMessageUseCase.findMessage(findMessageCommand);

    requireMessageToNotBeDelivered(message);

    persistMessagePort.deleteMessage(message.getId());
  }

  private void requireMessageToNotBeDelivered(Message message) throws MessageAlreadySentException {
    if (message.isDelivered()) {
      throw new MessageAlreadySentException(message.getId());
    }
  }
}
