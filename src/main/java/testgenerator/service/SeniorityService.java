package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Seniority;
import testgenerator.model.enums.Status;
import testgenerator.repository.SeniorityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeniorityService {

    private final SeniorityRepository repository;

    public Optional<Seniority> findById(Long id, Status status) {
        return repository.findByIdAndStatus(id, status);
    }

    public Page<Seniority> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Seniority add(Seniority seniority) {
        return repository.save(seniority);
    }

}
