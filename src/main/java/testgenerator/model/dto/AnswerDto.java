package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long id;

    private String answer;

    private Boolean isCorrect;

    private QuestionDto question;

}
