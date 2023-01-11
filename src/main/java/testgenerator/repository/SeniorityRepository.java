package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.SeniorityEntity;

@Repository
public interface SeniorityRepository extends JpaRepository<SeniorityEntity, Long> {
}
