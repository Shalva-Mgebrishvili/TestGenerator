package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.AnswerEntity;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
