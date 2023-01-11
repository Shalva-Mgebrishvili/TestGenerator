package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Test;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionDto {

    private Question question;

    private Test test;

    private List<CandidateAnswer> candidateAnswers;

}
