package io.joaoseidel.message.adapter.web.controller;

import io.joaoseidel.message.adapter.web.model.CreateMessageModel;
import io.joaoseidel.message.application.exceptions.InvalidScheduleDateException;
import io.joaoseidel.message.application.exceptions.MessageAlreadySentException;
import io.joaoseidel.message.application.exceptions.MessageNotFoundException;
import io.joaoseidel.message.application.port.in.CancelMessageUseCase;
import io.joaoseidel.message.application.port.in.CancelMessageUseCase.CancelMessageCommand;
import io.joaoseidel.message.application.port.in.FindMessageUseCase;
import io.joaoseidel.message.application.port.in.FindMessageUseCase.FindMessageCommand;
import io.joaoseidel.message.application.port.in.ScheduleMessageUseCase;
import io.joaoseidel.message.application.port.in.ScheduleMessageUseCase.ScheduleMessageCommand;
import io.joaoseidel.message.domain.Message;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/messages")
@Tag(name = "Message", description = "Message scheduler")
public class MessageController {

  private final ScheduleMessageUseCase scheduleMessageUseCase;
  private final FindMessageUseCase findMessageUseCase;
  private final CancelMessageUseCase cancelMessageUseCase;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> create(@RequestBody @Parameter CreateMessageModel model)
      throws InvalidScheduleDateException {
    var command =
        new ScheduleMessageCommand(
            model.getTarget(), model.getBody(), model.getType(), model.getScheduleDate());
    var message = scheduleMessageUseCase.scheduleMessage(command);
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
  }

  @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> find(
      @PathVariable("code") @Parameter(description = "The message identifier") String code)
      throws MessageNotFoundException {
    var command = new FindMessageCommand(code);
    var message = findMessageUseCase.findMessage(command);
    return ResponseEntity.ok(message);
  }

  @DeleteMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(
      @PathVariable("code") @Parameter(description = "The message identifier") String code)
      throws MessageNotFoundException, MessageAlreadySentException {
    var command = new CancelMessageCommand(code);
    cancelMessageUseCase.cancelMessage(command);
    return ResponseEntity.noContent().build();
  }
}
