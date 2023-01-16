package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Stack;
import testgenerator.model.enums.Status;
import testgenerator.repository.StackRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StackService {

    private final StackRepository repository;

    public Stack findById(Long id, Status status) {
        Optional<Stack> stack = repository.findByIdAndStatus(id, status);

        if (stack.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack with ID: " + id + " not found.");

        return stack.get();
    }

    public Page<Stack> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Stack add(Stack stack) {
        return repository.save(stack);
    }
}
