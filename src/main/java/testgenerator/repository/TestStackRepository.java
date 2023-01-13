package testgenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testgenerator.model.domain.TestStack;
import testgenerator.model.enums.Status;

import java.util.Optional;

@Repository
public interface TestStackRepository extends JpaRepository<TestStack, Long> {
    @Query("""
        SELECT ts
        FROM TestStack ts
        WHERE ts.id = :teststack_id
        AND ts.status = :status
        """)
    Optional<TestStack> findByIdAndStatus(
            @Param("teststack_id") Long testStackId,
            @Param("status") Status status
    );

    @Query("""
        SELECT ts
        FROM TestStack ts
        WHERE ts.status = :status
        order by ts.id 
        """)
    Page<TestStack> findAllByStatus(
            @Param("status") Status status,
            Pageable pageable
    );
}
