package io.joaoseidel.message.usecase;

import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.in.FindMessageUseCase.FindMessageCommand;
import io.joaoseidel.message.application.port.out.FindMessageByIdPort;
import io.joaoseidel.message.application.service.FindMessageService;
import io.joaoseidel.message.domain.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindMessageUseCaseTest {

  @InjectMocks private FindMessageService findMessageService;

  @Mock private FindMessageByIdPort findMessageByIdPort;

  @Test
  void givenAnId_whenFindMessage_thenReturnMessage() throws MessageNotFoundException {
    var code = "ba670fc0-4931-4a9a-8e9d-a5b81a00f45c";
    var message = Message.builder().build();

    when(findMessageByIdPort.findMessageById(code)).thenReturn(message);

    var command = new FindMessageCommand(code);
    assertThat(findMessageService.findMessage(command)).isEqualTo(message);

    verify(findMessageByIdPort).findMessageById(code);
  }

  @Test
  void givenAnInvalidId_whenFindMessage_thenThrowNotFoundException()
      throws MessageNotFoundException {
    var code = "ba670fc0-4931-4a9a-8e9d-a5b81a00f45c";

    when(findMessageByIdPort.findMessageById(code)).thenThrow(new MessageNotFoundException(code));

    var command = new FindMessageCommand(code);
    assertThatThrownBy(() -> findMessageService.findMessage(command))
        .isInstanceOf(MessageNotFoundException.class)
        .hasMessage(String.format("Message with ID '%s' does not exist!", code));

    verify(findMessageByIdPort).findMessageById(code);
  }
}
