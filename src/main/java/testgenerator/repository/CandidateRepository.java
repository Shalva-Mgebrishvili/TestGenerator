package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Candidate;
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
//
//    @Query("""
//        SELECT c
//        FROM Candidate c
//        WHERE c.status = :status
//        """)
//    Page<Candidate> findAllByStatus(
//            @Param("status") Status status,
//            Pageable pageable
//    );
}
