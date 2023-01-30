package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.TestResult;
import testgenerator.model.enums.Status;
import testgenerator.repository.TestResultRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultService {

    private final TestResultRepository repository;

    public TestResult findById(Long id, Status status) {
        Optional<TestResult> testResult = repository.findByIdAndStatus(id, status);

        if(testResult.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TestResult with ID: " + id + " not found.");

        return testResult.get();
    }

    public Page<TestResult> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Page<TestResult> findAllByUserId(Status status, Long userId, Pageable pageable) {
        return repository.findAllByUserId(status, userId, pageable);
    }

    public TestResult add(TestResult testResult) {
        return repository.save(testResult);
    }
}
