package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.AnswerEntity;
import testgenerator.model.domain.CandidateAnswerEntity;
import testgenerator.model.domain.TestQuestionEntity;
import testgenerator.model.domain.TestResultEntity;

@Data
@NoArgsConstructor
public class CandidateAnswerDto {

    private Long candidateAnswerId;

    private String answer;

    private Integer candidatePoint;

    private TestQuestionEntity testQuestion;

    private AnswerEntity chosenAnswer;

    private TestResultEntity testResult;

    public CandidateAnswerDto(CandidateAnswerEntity candidateAnswer) {
        BeanUtils.copyProperties(candidateAnswer, this);
    }
}
