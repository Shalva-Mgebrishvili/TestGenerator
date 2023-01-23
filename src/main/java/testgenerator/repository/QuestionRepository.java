package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;

import java.util.List;
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
            @Param("question_id") Long questionId,
            @Param("status") Status status
    );

    @Query("""
        SELECT q
        FROM Question q
        WHERE q.status = :status
        """)
    Page<Question> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
        SELECT q
        FROM Question q
        WHERE q.status = :status
        AND q.questionType = :question_type
        AND q.topic = :topic
        AND q.seniority = :seniority
        """)
    List<Question> findByQuestionTypeByTopicBySeniority(
            @Param("status") Status status,
            @Param("question_type") QuestionType questionType,
            @Param("topic") Topic topic,
            @Param("seniority") Seniority seniority
            );
}
