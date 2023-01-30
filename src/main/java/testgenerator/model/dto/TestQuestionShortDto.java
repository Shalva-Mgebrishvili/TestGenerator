package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testgenerator.model.enums.CandidateAnswerCorrectness;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionShortDto {
    private QuestionShortDto question;

    private CandidateAnswerCorrectness candidateAnswerCorrectness;
}
