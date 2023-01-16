package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Answer;
import testgenerator.model.enums.Status;
import testgenerator.repository.AnswerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository repository;

    public Answer findById(Long id, Status status) {
        Optional<Answer> answer = repository.findByIdAndStatus(id, status);

        if(answer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer with ID: " + id + " not found.");

        return answer.get();
    }

    public Page<Answer> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Answer add(Answer answer) {
        return repository.save(answer);
    }
}
