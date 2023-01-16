package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {

    @Query("""
        SELECT t
        FROM TestQuestion t
        WHERE t.id = :testQuestion_id
        AND t.status = :status
        """)
    Optional<TestQuestion> findByIdAndStatus(
            @Param("testQuestion_id") Long TestQuestionId,
            @Param("status") Status status
    );

    @Query("""
        SELECT t
        FROM TestQuestion t
        WHERE t.status = :status
        """)
    Page<TestQuestion> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
