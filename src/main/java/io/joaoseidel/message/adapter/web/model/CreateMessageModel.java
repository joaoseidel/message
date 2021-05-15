package io.joaoseidel.message.adapter.web.model;

import io.joaoseidel.message.domain.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMessageModel {

  @Schema(description = "The message subject.", example = "Joao Seidel")
  private final String target;

  @Schema(description = "The message itself.", example = "Hello, World!")
  private final String body;

  @Schema(implementation = MessageType.class, description = "The type of the message.")
  private final String type;

  @Schema(description = "The schedule date of the message.")
  private final LocalDateTime scheduleDate;
}
