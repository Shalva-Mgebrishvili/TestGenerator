package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateAnswerAddParam {

    private String answer;

    private Long testQuestion;

    private Long chosenAnswer;

    private Long candidate;
}
