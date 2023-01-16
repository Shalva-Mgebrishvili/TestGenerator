package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.enums.Status;
import testgenerator.repository.CandidateAnswerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateAnswerService {

    private final CandidateAnswerRepository candidateAnswerRepository;

    public CandidateAnswer findById(Long id, Status status) {
        Optional<CandidateAnswer> candidateAnswer = candidateAnswerRepository.findByIdAndStatus(id, status);

        if(candidateAnswer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CandidateAnswer with ID: " + id + " not found.");

        return candidateAnswer.get();
    }

    public Page<CandidateAnswer> findAll(Status status, Pageable pageable) {
        return candidateAnswerRepository.findAllByStatus(status, pageable);
    }

    public CandidateAnswer add(CandidateAnswer candidateAnswer) {
        return candidateAnswerRepository.save(candidateAnswer);
    }
}
