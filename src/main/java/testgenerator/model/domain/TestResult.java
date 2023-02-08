package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult extends SuperEntity {

    @Column(name = "candidate_test_start_date")
    private LocalDateTime candidateTestStartDate;

    @Column(name = "candidate_test_finish_date")
    private LocalDateTime candidateTestFinishDate;

    @Column(name = "time_needed")
    private Long timeNeeded;

    @Column(name = "total_point")
    private Double totalPoint;

    @Column(name = "candidate_score")
    private Double candidateScore;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToMany
    @JoinTable(name = "reviewer_testResult",
    joinColumns = @JoinColumn(name = "testResult_id"),
    inverseJoinColumns = @JoinColumn(name = "reviewer_id"))
    private List<UserEntity> reviewer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

}
