package io.joaoseidel.message.application.port.in;

import io.joaoseidel.message.application.exceptions.InvalidScheduleDateException;
import io.joaoseidel.message.common.SelfValidating;
import io.joaoseidel.message.domain.Message;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface ScheduleMessageUseCase {

  Message scheduleMessage(ScheduleMessageCommand command) throws InvalidScheduleDateException;

  @EqualsAndHashCode(callSuper = false)
  @Data
  final class ScheduleMessageCommand extends SelfValidating<ScheduleMessageCommand> {

    @NotBlank private final String target;
    @NotBlank private final String body;

    @NotNull
    @Pattern(regexp = "^(EMAIL|SMS|PUSH|WHATSAPP)$", message = "Invalid enum type.")
    private final String type;

    @NotNull private final LocalDateTime scheduleDate;

    public ScheduleMessageCommand(
        @NotEmpty String target,
        @NotEmpty String body,
        @NotNull String type,
        @NotNull LocalDateTime scheduleDate) {
      this.target = target;
      this.body = body;
      this.type = type;
      this.scheduleDate = scheduleDate;
      validateSelf();
    }
  }
}
