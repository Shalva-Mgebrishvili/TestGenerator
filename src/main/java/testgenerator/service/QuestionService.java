package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.enums.Status;
import testgenerator.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> findById(Long id, Status status) {
        return questionRepository.findByIdAndStatus(id, status);
    }

    public Page<Question> findAll(Status status) {
        return questionRepository.findAllByStatus(status, PageRequest.ofSize(10));
    }

    public Question add(Question questionEntity) {
        return questionRepository.save(questionEntity);
    }
}
