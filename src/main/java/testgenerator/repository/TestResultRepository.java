package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestResult;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    @Query("""
        SELECT t
        FROM TestResult t
        WHERE t.id = :testResult_id
        AND t.status = :status
        """)
    Optional<TestResult> findByIdAndStatus(
            @Param("testResult_id") Long TestResultId,
            @Param("status") Status status
    );

    @Query("""
        SELECT t
        FROM TestResult t
        WHERE t.status = :status
        """)
    Page<TestResult> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
        SELECT t
        FROM TestResult t
        WHERE t.status = :status
        AND t.user.id = :user_id
        """)
    Page<TestResult> findAllByUserId(
            @Param("status") Status status,
            @Param("user_id") Long userId,
            Pageable pageable
    );
}
