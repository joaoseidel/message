package io.joaoseidel.message.adapter.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.joaoseidel.message.adapter.entity.MessageEntity;
import io.joaoseidel.message.domain.Message;
import io.joaoseidel.message.domain.MessageType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageEntityMapperTest {

  private MessageEntityMapper messageEntityMapper;

  @BeforeEach
  void setup() {
    messageEntityMapper = new MessageEntityMapper();
  }

  @Test
  void givenAnMessage_whenMap_thenReturnEntity() {
    var message =
        Message.builder()
            .id(UUID.randomUUID().toString())
            .target("me@joaoseidel.io")
            .type(MessageType.EMAIL)
            .body("Hello, World!")
            .delivered(false)
            .scheduleDate(LocalDateTime.now().plusMinutes(2))
            .build();

    var entity = messageEntityMapper.from(message);

    assertThat(entity.getId()).isEqualTo(message.getId());
    assertThat(entity.getTarget()).isEqualTo(message.getTarget());
    assertThat(entity.getType()).isEqualTo(message.getType());
    assertThat(entity.getBody()).isEqualTo(message.getBody());
    assertThat(entity.isDelivered()).isEqualTo(message.isDelivered());
    assertThat(entity.getScheduleDate()).isEqualTo(message.getScheduleDate());
  }

  @Test
  void givenAnEntity_whenMap_thenReturnMessage() {
    var entity =
        MessageEntity.builder()
            .id(UUID.randomUUID().toString())
            .target("me@joaoseidel.io")
            .type(MessageType.EMAIL)
            .body("Hello, World!")
            .delivered(true)
            .scheduleDate(LocalDateTime.now())
            .build();

    var message = messageEntityMapper.to(entity);
    assertThat(message.getId()).isEqualTo(entity.getId());
    assertThat(message.getTarget()).isEqualTo(entity.getTarget());
    assertThat(message.getType()).isEqualTo(entity.getType());
    assertThat(message.getBody()).isEqualTo(entity.getBody());
    assertThat(message.isDelivered()).isEqualTo(entity.isDelivered());
    assertThat(message.getScheduleDate()).isEqualTo(entity.getScheduleDate());
  }
}
