package io.joaoseidel.message.adapter.persistence;

import io.joaoseidel.message.adapter.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {}
