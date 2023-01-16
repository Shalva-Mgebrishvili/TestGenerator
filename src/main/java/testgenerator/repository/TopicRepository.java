package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Topic;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("""
        SELECT t
        FROM Topic t
        WHERE t.id = :topic_id
        AND t.status = :status
        """)
    Optional<Topic> findByIdAndStatus(
            @Param("topic_id") Long topicId,
            @Param("status") Status status
    );

    @Query("""
        SELECT t
        FROM Topic t
        WHERE t.status = :status
        """)
    Page<Topic> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
