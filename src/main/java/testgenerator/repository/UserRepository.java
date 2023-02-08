package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.id = :user_id
        AND u.status = :status
        """)
    Optional<UserEntity> findByIdAndStatus(
            @Param("user_id") Long userId,
            @Param("status") Status status
    );

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.status = :status
        """)
    Page<UserEntity> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.email = :email
        AND u.status = :status
        """)
    Optional<UserEntity> existsByEmailAndStatus(
        @Param("email") String email,
        @Param("status") Status status
    );

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.username = :username
        AND u.status = :status
        """)
    Optional<UserEntity> existsByUsernameAndStatus(
            @Param("username") String username,
            @Param("status") Status status
    );

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.email = :email
        AND u.status = :status
        """)
    Optional<UserEntity> findByEmailAndStatus(
            @Param("email") String email,
            @Param("status") Status status
    );

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.id = :user_id
        AND u.status = :status
        OR u.status = :status2
        """)
    Optional<UserEntity> findOnlyById(
            @Param("user_id") Long userId,
            @Param("status") Status  status,
            @Param("status2") Status  status2
    );
}
