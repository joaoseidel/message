package io.joaoseidel.message.adapter.mapper;

import io.joaoseidel.message.adapter.entity.MessageEntity;
import io.joaoseidel.message.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageEntityMapper {

  public MessageEntity from(Message message) {
    return MessageEntity.builder()
        .id(message.getId())
        .target(message.getTarget())
        .body(message.getBody())
        .type(message.getType())
        .delivered(message.isDelivered())
        .scheduleDate(message.getScheduleDate())
        .build();
  }

  public Message to(MessageEntity entity) {
    return Message.builder()
        .id(entity.getId())
        .target(entity.getTarget())
        .body(entity.getBody())
        .type(entity.getType())
        .delivered(entity.isDelivered())
        .scheduleDate(entity.getScheduleDate())
        .build();
  }
}
