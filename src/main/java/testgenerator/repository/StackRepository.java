package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.StackEntity;

@Repository
public interface StackRepository extends JpaRepository<StackEntity, Long> {
}
