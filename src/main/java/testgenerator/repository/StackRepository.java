package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.Stack;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {

    @Query("""
        SELECT s
        FROM Stack s
        WHERE s.id = :stack_id
        AND s.status = :status
        """)
    Optional<Stack> findByIdAndStatus(
            @Param("stack_id") Long stackId,
            @Param("status") Status status
    );

    @Query("""
        SELECT s
        FROM Stack s
        WHERE s.status = :status
        order by s.id 
        """)
    Page<Stack> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
