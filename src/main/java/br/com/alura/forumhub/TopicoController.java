package br.com.alura.forumhub;

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
    public ResponseEntity createTopic(@RequestBody @Valid TopicDataReceiverDTO data, UriComponentsBuilder uriBuilder) {
        var topic = new Topic(data);
        var CONTEXT_AUTHOR = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        topic.setAuthor(CONTEXT_AUTHOR);
        topicRepository.save(topic);
        var location = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(location).body(new TopicCreatedDAO(topic));
    }
}
