package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
