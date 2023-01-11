package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.AnswerEntity;
import testgenerator.model.domain.Question;

@Data
@NoArgsConstructor
public class AnswerDto {

    private Long answerId;

    private String answer;

    private Boolean isCorrect;

    private Question question;

    public AnswerDto(AnswerEntity answer) {
        BeanUtils.copyProperties(answer, this);
    }
}
