package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerAddParam {

    private String answer;

    private Boolean isCorrect;

    private Long question;
}
