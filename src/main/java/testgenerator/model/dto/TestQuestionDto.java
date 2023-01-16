package testgenerator.model.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionDto {

    private Long id;

    private QuestionDto question;

    private TestDto test;

    private List<CandidateAnswerDto> candidateAnswers;

}
