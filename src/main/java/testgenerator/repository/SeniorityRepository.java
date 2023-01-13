package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface SeniorityRepository extends JpaRepository<Seniority, Long> {

    @Query("""
        SELECT s
        FROM Seniority s
        WHERE s.id = :seniority_id
        AND s.status = :status
        """)
    Optional<Seniority> findByIdAndStatus(
            @Param("seniority_id") Long seniorityId,
            @Param("status") Status status
    );

    @Query("""
        SELECT s
        FROM Seniority s
        WHERE s.status = :status
        order by s.id 
        """)
    Page<Seniority> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
