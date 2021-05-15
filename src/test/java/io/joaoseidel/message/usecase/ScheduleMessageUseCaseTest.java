package io.joaoseidel.message.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.joaoseidel.message.adapter.web.model.CreateMessageModel;
import io.joaoseidel.message.application.exceptions.InvalidScheduleDateException;
import io.joaoseidel.message.application.port.in.ScheduleMessageUseCase.ScheduleMessageCommand;
import io.joaoseidel.message.application.port.out.PersistMessagePort;
import io.joaoseidel.message.application.service.ScheduleMessageService;
import io.joaoseidel.message.domain.Message;
import io.joaoseidel.message.domain.MessageType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleMessageUseCaseTest {

  @InjectMocks private ScheduleMessageService scheduleMessageService;

  @Mock private PersistMessagePort persistMessagePort;

  @Test
  void givenAMessage_WhenSchedule_ThenReturnScheduledMessage() throws InvalidScheduleDateException {
    var message =
        CreateMessageModel.builder()
            .target("me@joaoseidel.io")
            .body("Hello, World!")
            .type(MessageType.EMAIL.name())
            .scheduleDate(LocalDateTime.now().plusMinutes(2))
            .build();

    when(persistMessagePort.scheduleMessage(any(Message.class)))
        .thenAnswer(answer -> answer.getArgument(0));

    var command =
        new ScheduleMessageCommand(
            message.getTarget(), message.getBody(), message.getType(), message.getScheduleDate());
    var scheduledMessage = scheduleMessageService.scheduleMessage(command);

    assertThat(scheduledMessage.getId()).isNull();
    assertThat(scheduledMessage.getTarget()).isEqualTo(message.getTarget());
    assertThat(scheduledMessage.getBody()).isEqualTo(message.getBody());
    assertThat(scheduledMessage.getType().name()).isEqualTo(message.getType());
    assertThat(scheduledMessage.getScheduleDate()).isEqualTo(message.getScheduleDate());
    assertThat(scheduledMessage.isDelivered()).isFalse();

    verify(persistMessagePort).scheduleMessage(scheduledMessage);
  }

  @Test
  void givenAMessageWithInvalidScheduledDate_whenSchedule_thenThrowInvalidScheduleDateException() {
    var message =
        CreateMessageModel.builder()
            .target("me@joaoseidel.io")
            .body("Hello, World!")
            .type(MessageType.EMAIL.name())
            .scheduleDate(LocalDateTime.now().minusMinutes(2))
            .build();

    var command =
        new ScheduleMessageCommand(
            message.getTarget(), message.getBody(), message.getType(), message.getScheduleDate());
    assertThatThrownBy(() -> scheduleMessageService.scheduleMessage(command))
        .isInstanceOf(InvalidScheduleDateException.class)
        .hasMessage("Message does not have a valid schedule date!");
  }
}
