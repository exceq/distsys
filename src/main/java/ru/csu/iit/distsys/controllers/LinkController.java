package ru.csu.iit.distsys.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.csu.iit.distsys.controllers.commands.CreateLinkCommand;
import ru.csu.iit.distsys.controllers.commands.UpdateLinkCommand;
import ru.csu.iit.distsys.domain.Link;
import ru.csu.iit.distsys.repositories.LinkRepository;

@ApiOperation(value = "Контроллер для работы с ссылками", notes = "123")
@RestController
@RequestMapping("/link")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class LinkController {
    // Вообще лучше из контроллера обращаться к Service,
    // а тот, в свою очередь, обращается к Repository
    private final LinkRepository linkRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    @ApiOperation(value = "Возвращает ссылку по id")
    @GetMapping("{id}")
    public ResponseEntity<Link> getLink(@PathVariable Long id) throws IllegalArgumentException {
        return linkRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Link with id=%d not found", id)));
    }

    @PostMapping
    public ResponseEntity<Link> createLink(@RequestBody CreateLinkCommand command) throws JsonProcessingException {
        Link link = new Link();
        link.setUrl(command.getUrl());
        linkRepository.save(link);

        rabbitTemplate.convertAndSend("links", objectMapper.writer().writeValueAsString(link));

        return ResponseEntity.ok(link);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Link> updateLink(@PathVariable Long id, @RequestBody UpdateLinkCommand command) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Link with id=%d not found", id)));
        link.setStatus(command.getStatus());
        linkRepository.save(link);
        return ResponseEntity.ok(link);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ApiError(true, e.getMessage()));
    }

    @ApiModel(description = "Ошибка апи")
    @AllArgsConstructor
    static class ApiError {
        boolean error;
        String message;
    }
}
