package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity extends SuperEntity {

    @Column(name = "given_time")
    private Integer givenTime;

    @OneToMany(mappedBy = "test")
    private List<TestStackEntity> testStacks;

    @ManyToOne
    @JoinColumn(name = "seniority_id", nullable = false)
    private SeniorityEntity seniority;

    @OneToMany(mappedBy = "test")
    private List<TestQuestionEntity> testQuestions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "test_candidate",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    private List<CandidateEntity> candidates;

}
