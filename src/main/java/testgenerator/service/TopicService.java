package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Topic;
import testgenerator.model.enums.Status;
import testgenerator.repository.TopicRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;

    public Topic findById(Long id, Status status) {
        Optional<Topic> topic = repository.findByIdAndStatus(id, status);

        if (topic.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic with ID: " + id + " not found.");

        return topic.get();
    }

    public Page<Topic> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Topic add(Topic topic) {
        return repository.save(topic);
    }
}
