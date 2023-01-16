package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Candidate;
import testgenerator.model.enums.Status;
import testgenerator.repository.CandidateRepository;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {

    private final CandidateRepository repository;

    public Candidate findById(Long id, Status status) {
        Optional<Candidate> candidate = repository.findByIdAndStatus(id, status);

        if(candidate.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with ID: " + id + " not found.");

        return candidate.get();
    }

    public Page<Candidate> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Candidate add(Candidate candidate) {
        return repository.save(candidate);
    }
}
