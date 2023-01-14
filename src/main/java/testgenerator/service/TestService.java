package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Test;
import testgenerator.model.enums.Status;
import testgenerator.repository.TestRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository repository;

    public Test findById(Long id, Status status) {
        Optional<Test> test = repository.findByIdAndStatus(id, status);

        if(test.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test with ID: " + id + " not found.");

        return test.get();
    }

    public Page<Test> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Test add(Test test) {
        return repository.save(test);
    }

}
