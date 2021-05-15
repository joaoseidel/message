package io.joaoseidel.message.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.joaoseidel.message.application.exceptions.MessageAlreadySentException;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.in.CancelMessageUseCase.CancelMessageCommand;
import io.joaoseidel.message.application.port.in.FindMessageUseCase;
import io.joaoseidel.message.application.port.in.FindMessageUseCase.FindMessageCommand;
import io.joaoseidel.message.application.port.out.PersistMessagePort;
import io.joaoseidel.message.application.service.CancelMessageService;
import io.joaoseidel.message.domain.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CancelMessageUseCaseTest {

  @InjectMocks private CancelMessageService cancelMessageService;

  @Mock private FindMessageUseCase findMessageUseCase;

  @Mock private PersistMessagePort persistMessagePort;

  @Test
  void givenMessage_WhenCancel_ThenCallPersistPort()
      throws MessageNotFoundException, MessageAlreadySentException {
    var code = "ba670fc0-4931-4a9a-8e9d-a5b81a00f45c";
    var message = Message.builder().id(code).delivered(false).build();

    var command = new FindMessageCommand(code);
    when(findMessageUseCase.findMessage(command)).thenReturn(message);

    var cancelCommand = new CancelMessageCommand(code);
    cancelMessageService.cancelMessage(cancelCommand);

    verify(findMessageUseCase).findMessage(command);
    verify(persistMessagePort).deleteMessage(code);
  }

  @Test
  void givenADeliveredMessage_WhenCancel_ThenThrowAlreadySentException()
      throws MessageNotFoundException {
    var code = "ba670fc0-4931-4a9a-8e9d-a5b81a00f45c";
    var message = Message.builder().id(code).delivered(true).build();

    var command = new FindMessageCommand(code);
    when(findMessageUseCase.findMessage(command)).thenReturn(message);

    var cancelCommand = new CancelMessageCommand(code);
    assertThatThrownBy(() -> cancelMessageService.cancelMessage(cancelCommand))
        .isInstanceOf(MessageAlreadySentException.class)
        .hasMessage(String.format("The message with ID %s was already sent!", code));
  }
}
