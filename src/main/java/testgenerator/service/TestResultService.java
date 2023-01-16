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

    private final TestResultRepository testResultRepository;

    public TestResult findById(Long id, Status status) {
        Optional<TestResult> testResult = testResultRepository.findByIdAndStatus(id, status);

        if(testResult.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TestResult with ID: " + id + " not found.");

        return testResult.get();
    }

    public Page<TestResult> findAll(Status status, Pageable pageable) {
        return testResultRepository.findAllByStatus(status, pageable);
    }

    public TestResult add(TestResult testResult) {
        return testResultRepository.save(testResult);
    }
}
