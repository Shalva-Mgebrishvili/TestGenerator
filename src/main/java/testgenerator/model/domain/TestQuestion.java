package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion extends SuperEntity {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @OneToMany(mappedBy = "testQuestion")
    private List<CandidateAnswer> candidateAnswers;

}
