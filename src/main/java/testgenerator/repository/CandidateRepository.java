package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("""
        SELECT c
        FROM Candidate c
        WHERE c.id = :candidate_id
        AND c.status = :status
        """)
    Optional<Candidate> findByIdAndStatus(
            @Param("candidate_id") Long candidateId,
            @Param("status") Status status
    );

    @Query("""
        SELECT c
        FROM Candidate c
        WHERE c.status = :status
        """)
    Page<Candidate> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
        SELECT c
        FROM Candidate c
        WHERE c.user.id = :user_id
        AND c.status = :status
        """)
    Optional<Candidate> existsByUserIdAndStatus(
            @Param("user_id") Long userId,
            @Param("status") Status status
    );
}
