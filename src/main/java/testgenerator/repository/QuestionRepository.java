package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Question;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
        SELECT q
        FROM Question q
        WHERE q.id = :question_id
        AND q.status = :status
        """)
    Optional<Question> findByIdAndStatus(
            @Param("question_id") Long QuestionId,
            @Param("status") Status status
    );

    @Query("""
        SELECT q
        FROM Question q
        WHERE q.status = :status
        order by q.id 
        """)
    Page<Question> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
