package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
