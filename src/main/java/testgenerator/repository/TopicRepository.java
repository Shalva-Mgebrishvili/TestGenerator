package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
