package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Stack;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
}
