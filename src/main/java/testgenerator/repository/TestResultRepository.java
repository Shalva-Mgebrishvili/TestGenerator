package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
