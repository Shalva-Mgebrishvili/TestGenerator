package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestResultEntity;

@Repository
public interface TestResultRepository extends JpaRepository<TestResultEntity, Long> {
}
