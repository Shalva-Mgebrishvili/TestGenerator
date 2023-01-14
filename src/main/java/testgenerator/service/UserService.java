package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.enums.Status;
import testgenerator.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserEntity findById(Long id, Status status) {
        Optional<UserEntity> user = repository.findByIdAndStatus(id, status);

        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + id + " not found.");

        return user.get();
    }

    public Page<UserEntity> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public UserEntity add(UserEntity user) {
        return repository.save(user);
    }
}
