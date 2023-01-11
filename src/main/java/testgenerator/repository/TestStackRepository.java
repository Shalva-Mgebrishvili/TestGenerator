package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestStack;

@Repository
public interface TestStackRepository extends JpaRepository<TestStack, Long> {
}
