package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswer, Long> {

    @Query("""
        SELECT c
        FROM CandidateAnswer c
        WHERE c.id = :candidateAnswer_id
        AND c.status = :status
        """)
    Optional<CandidateAnswer> findByIdAndStatus(
            @Param("candidateAnswer_id") Long CandidateAnswerId,
            @Param("status") Status status
    );

    @Query("""
        SELECT c
        FROM CandidateAnswer c
        WHERE c.status = :status
        """)
    Page<CandidateAnswer> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
