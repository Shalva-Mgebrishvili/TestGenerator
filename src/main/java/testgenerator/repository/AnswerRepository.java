package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Answer;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("""
        SELECT a
        FROM Answer a
        WHERE a.id = :answer_id
        AND a.status = :status
        """)
    Optional<Answer> findByIdAndStatus(
            @Param("answer_id") Long answerId,
            @Param("status") Status status
    );

    @Query("""
        SELECT a
        FROM Answer a
        WHERE a.status = :status
        """)
    Page<Answer> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );

}
