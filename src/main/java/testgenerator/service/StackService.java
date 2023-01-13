package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Stack;
import testgenerator.model.enums.Status;
import testgenerator.repository.StackRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository repository;

    public Optional<Stack> findById(Long id, Status status) {
        return repository.findByIdAndStatus(id, status);
    }

    public Page<Stack> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Stack add(Stack stack) {
        return repository.save(stack);
    }
}
