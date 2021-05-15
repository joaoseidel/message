package io.joaoseidel.message.application.port.in;

import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.common.Constraints;
import io.joaoseidel.message.common.SelfValidating;
import io.joaoseidel.message.domain.Message;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface FindMessageUseCase {

  Message findMessage(FindMessageCommand code) throws MessageNotFoundException;

  @EqualsAndHashCode(callSuper = false)
  @Data
  final class FindMessageCommand extends SelfValidating<FindMessageCommand> {

    @NotBlank
    @Pattern(regexp = Constraints.UUID_REGEX, message = "Invalid code format.")
    private final String code;

    public FindMessageCommand(@NotBlank String code) {
      this.code = code;
      validateSelf();
    }
  }
}
