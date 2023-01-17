package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult extends SuperEntity {

    @Column(name = "test_start_date")
    private LocalDateTime testStartDate;

    @Column(name = "test_finish_date")
    private LocalDateTime testFinishDate;

    @Column(name = "time_needed")
    private Long timeNeeded;

    @Column(name = "total_point")
    private Double totalPoint;

    @Column(name = "candidate_score")
    private Double candidateScore;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity corrector;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

}
