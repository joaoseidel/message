package io.joaoseidel.message.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Message {

  private String id;
  private String target;
  private String body;
  private MessageType type;
  private boolean delivered;
  private LocalDateTime scheduleDate;
}
