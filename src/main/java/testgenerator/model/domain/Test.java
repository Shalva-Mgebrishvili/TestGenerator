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
public class Test extends SuperEntity {

    @Column(name = "given_time")
    private Long givenTime;

    @Column(name = "number_of_open_questions")
    private Integer numberOfOpenQuestions;

    @Column(name = "number_of_multiple_choice_test_questions")
    private Integer numberOfMultipleChoiceTestQuestions;

    @Column(name = "number_of_single_choice_test_questions")
    private Integer numberOfSingleChoiceTestQuestions;

    @ManyToOne
    @JoinColumn(name = "seniority_id", nullable = false)
    private Seniority seniority;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TestStack> testStacks;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    private List<TestQuestion> testQuestions;

}
