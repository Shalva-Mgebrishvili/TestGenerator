package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Test;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("""
        SELECT t 
        FROM Test t
        WHERE t.id = :test_id
        AND t.status = :status
        """)
    Optional<Test> findByIdAndStatus(
            @Param("test_id") Long testId,
            @Param("status") Status status
    );
}
