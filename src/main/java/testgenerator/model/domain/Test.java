package testgenerator.model.domain;

import lombok.*;
import testgenerator.model.enums.QuestionStatus;
import testgenerator.model.enums.TestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test extends SuperEntity {

    @Column(name = "given_time_in_minutes", nullable = false)
    private Long givenTimeInMinutes;

    @Column(name = "given_test_start_date", nullable = false)
    private LocalDateTime givenTestStartDate;

    @Column(name = "given_test_end_date", nullable = false)
    private LocalDateTime givenTestEndDate;

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

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TestQuestion> testQuestions;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TestResult> testResults;

    @Enumerated(EnumType.STRING)
    @Column(name = "test_status")
    private TestStatus testStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_status", nullable = false)
    private QuestionStatus questionStatus;

}
