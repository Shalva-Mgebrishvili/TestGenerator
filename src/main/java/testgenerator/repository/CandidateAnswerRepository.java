package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.CandidateAnswer;

@Repository
public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswer, Long> {
}
