package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "candidate_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAnswer extends SuperEntity {

    @Column(name = "answer")
    private String answer;

    @Column(name = "candidate_point")
    private Double candidatePoint;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "test_question_id")
    private TestQuestion testQuestion;

    @ManyToOne
    @JoinColumn(name = "chosen_answer_id")
    private Answer chosenAnswer;

//    @ManyToOne
//    @JoinColumn(name = "candidate_id")
//    private Candidate candidate;

}
