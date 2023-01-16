package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateAnswerParam {

    private String answer;

    private Integer candidatePoint;

    private Long testQuestion;

    private Long chosenAnswer;

}
