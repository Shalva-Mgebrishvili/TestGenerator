package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.TestStack;
import testgenerator.model.enums.Status;
import testgenerator.repository.TestStackRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestStackService {
    private final TestStackRepository repository;

    public TestStack findById(Long id, Status status) {
        Optional<TestStack> testStack = repository.findByIdAndStatus(id, status);

        if(testStack.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test stack with ID: " + id + " not found.");

        return testStack.get();
    }

    public Page<TestStack> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public TestStack add(TestStack testStack) {
        return repository.save(testStack);
    }
}
