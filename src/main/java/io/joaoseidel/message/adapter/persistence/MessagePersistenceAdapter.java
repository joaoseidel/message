package io.joaoseidel.message.adapter.persistence;

import io.joaoseidel.message.adapter.mapper.MessageEntityMapper;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.out.FindMessageByIdPort;
import io.joaoseidel.message.application.port.out.PersistMessagePort;
import io.joaoseidel.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePersistenceAdapter implements FindMessageByIdPort, PersistMessagePort {

  private final MessageRepository messageRepository;
  private final MessageEntityMapper entityMapper;

  @Override
  public Message findMessageById(String code) throws MessageNotFoundException {
    var entity =
        messageRepository.findById(code).orElseThrow(() -> new MessageNotFoundException(code));
    return entityMapper.to(entity);
  }

  @Override
  public Message scheduleMessage(Message message) {
    var entity = messageRepository.save(entityMapper.from(message));
    return entityMapper.to(entity);
  }

  @Override
  public void deleteMessage(String code) throws MessageNotFoundException {
    var entity = findMessageById(code);
    messageRepository.delete(entityMapper.from(entity));
  }
}
