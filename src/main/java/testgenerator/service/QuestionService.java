package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.enums.Status;
import testgenerator.repository.QuestionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> findById(Long id, Status status) {
        return questionRepository.findByIdAndStatus(id, status);
    }

    public Page<Question> findAll(Status status, Pageable pageable) {
        return questionRepository.findAllByStatus(status, pageable);
    }

    public Question add(Question question) {
        return questionRepository.save(question);
    }
}
