package io.joaoseidel.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
