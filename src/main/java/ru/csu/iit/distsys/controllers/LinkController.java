package ru.csu.iit.distsys.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.csu.iit.distsys.controllers.commands.CreateLinkCommand;
import ru.csu.iit.distsys.controllers.commands.UpdateLinkCommand;
import ru.csu.iit.distsys.domain.Link;
import ru.csu.iit.distsys.repositories.LinkRepository;

import java.util.Optional;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LinkController {
    // Вообще лучше из контроллера обращаться к Service,
    // а тот, в свою очередь, обращается к Repository
    private final LinkRepository linkRepository;


    @GetMapping("{id}")
    public ResponseEntity<Link> getLink(@PathVariable Long id) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isPresent())
            return ResponseEntity.ok(link.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Link> createLink(@RequestBody CreateLinkCommand command) {
        Link link = new Link();
        link.setUrl(command.getUrl());
        linkRepository.save(link);

        return ResponseEntity.ok(link);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Link> updateLink(@PathVariable Long id, @RequestBody UpdateLinkCommand command) {
        Link link = linkRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        link.setStatus(command.getStatus());
        linkRepository.save(link);
        return ResponseEntity.ok(link);
    }
}
