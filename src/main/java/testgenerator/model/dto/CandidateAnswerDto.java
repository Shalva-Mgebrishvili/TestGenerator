package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Answer;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestResult;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAnswerDto {

    private Long id;

    private String answer;

    private Integer candidatePoint;

    private TestQuestion testQuestion;

    private Answer chosenAnswer;

}
