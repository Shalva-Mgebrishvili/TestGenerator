package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestQuestionEntity;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestionEntity, Long> {
}
