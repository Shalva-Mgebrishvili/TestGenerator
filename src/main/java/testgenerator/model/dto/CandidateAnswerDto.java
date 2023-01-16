package testgenerator.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAnswerDto {

    private Long id;

    private String answer;

    private Integer candidatePoint;

    private Long testQuestion;

    private Long chosenAnswer;

}
