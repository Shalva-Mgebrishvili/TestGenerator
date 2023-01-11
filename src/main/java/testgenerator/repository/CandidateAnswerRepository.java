package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.CandidateAnswerEntity;

@Repository
public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswerEntity, Long> {
}
