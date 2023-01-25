package testgenerator.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAnswerDto {

    private Long id;

    private String answer;

    private Double candidatePoint;

//    private TestQuestionDto testQuestion;

    private AnswerDto chosenAnswer;

//    private CandidateDto candidate;

}
