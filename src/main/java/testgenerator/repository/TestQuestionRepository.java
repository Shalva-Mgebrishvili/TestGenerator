package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestQuestion;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
}
