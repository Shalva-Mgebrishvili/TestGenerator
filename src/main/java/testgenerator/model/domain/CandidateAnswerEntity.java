package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "candidate_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAnswerEntity extends SuperEntity {

    @Column(name = "answer")
    private String answer;

    @Column(name = "candidate_point")
    private Integer candidatePoint;

    @ManyToOne
    @JoinColumn(name = "test_question_id")
    private TestQuestionEntity testQuestion;

    @ManyToOne
    @JoinColumn(name = "chosen_answer_id")
    private AnswerEntity chosenAnswer;

}
