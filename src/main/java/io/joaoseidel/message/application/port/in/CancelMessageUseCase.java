package io.joaoseidel.message.application.port.in;

import io.joaoseidel.message.application.exceptions.MessageAlreadySentException;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.common.Constraints;
import io.joaoseidel.message.common.SelfValidating;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public interface CancelMessageUseCase {

  void cancelMessage(CancelMessageCommand command)
      throws MessageNotFoundException, MessageAlreadySentException;

  @EqualsAndHashCode(callSuper = false)
  @Data
  final class CancelMessageCommand extends SelfValidating<FindMessageUseCase.FindMessageCommand> {

    @NotBlank
    @Pattern(regexp = Constraints.UUID_REGEX, message = "Invalid code format.")
    private final String code;

    public CancelMessageCommand(@NotBlank String code) {
      this.code = code;
      validateSelf();
    }
  }
}
