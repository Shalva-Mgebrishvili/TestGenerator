package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Test;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.enums.Status;
import testgenerator.repository.TestQuestionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestQuestionService {

    private final TestQuestionRepository repository;

    public TestQuestion findById(Long id, Status status) {
        Optional<TestQuestion> testQuestion = repository.findByIdAndStatus(id, status);

        if(testQuestion.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TestQuestion with ID: " + id + " not found.");

        return testQuestion.get();
    }

    public Page<TestQuestion> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public TestQuestion add(TestQuestion testQuestion) {
        return repository.save(testQuestion);
    }

    public Double getPointSum(Test test) {
        return repository.getPointSum(Status.ACTIVE, test);
    }

    public Double getCurrentPointSum(Test test) {
        return repository.getCurrentPointSum(Status.ACTIVE, test);
    }
}
