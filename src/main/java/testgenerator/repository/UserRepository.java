package testgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
