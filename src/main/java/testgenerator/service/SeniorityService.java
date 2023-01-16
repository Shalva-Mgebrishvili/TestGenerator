package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Seniority;
import testgenerator.model.enums.Status;
import testgenerator.repository.SeniorityRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeniorityService {

    private final SeniorityRepository repository;

    public Seniority findById(Long id, Status status) {
        Optional<Seniority> seniority = repository.findByIdAndStatus(id,status);

        if(seniority.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with ID: " + id + " not found.");

        return seniority.get();
    }

    public Page<Seniority> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Seniority add(Seniority seniority) {
        return repository.save(seniority);
    }

}
