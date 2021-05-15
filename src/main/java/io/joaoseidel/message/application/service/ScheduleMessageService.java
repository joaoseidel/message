package io.joaoseidel.message.application.service;

import io.joaoseidel.message.application.exceptions.InvalidScheduleDateException;
import io.joaoseidel.message.application.port.in.ScheduleMessageUseCase;
import io.joaoseidel.message.application.port.out.PersistMessagePort;
import io.joaoseidel.message.domain.Message;
import io.joaoseidel.message.domain.MessageType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleMessageService implements ScheduleMessageUseCase {

  private final PersistMessagePort saveMessagePort;

  @Override
  public Message scheduleMessage(ScheduleMessageCommand command)
      throws InvalidScheduleDateException {

    requirePostScheduleDate(command.getScheduleDate());

    final Message message =
        Message.builder()
            .target(command.getTarget())
            .body(command.getBody())
            .scheduleDate(command.getScheduleDate())
            .type(MessageType.valueOf(command.getType()))
            .build();
    return saveMessagePort.scheduleMessage(message);
  }

  private void requirePostScheduleDate(LocalDateTime scheduleDate)
      throws InvalidScheduleDateException {
    if (LocalDateTime.now().isAfter(scheduleDate)) {
      throw new InvalidScheduleDateException();
    }
  }
}
