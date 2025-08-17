package br.com.alura.forumhub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @PostMapping
    @Transactional
    public ResponseEntity createTopic(@RequestBody @Valid TopicoDataReceiverDTO data, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(data);
        var CONTEXT_AUTHOR = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        topico.setAuthor(CONTEXT_AUTHOR);
        topicoRepository.save(topico);
        var location = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(location).body(new TopicoCreatedDAO(topico));
    }
}
