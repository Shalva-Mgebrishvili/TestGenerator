package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.enums.QuestionStatus;
import testgenerator.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
public class QuestionAddUpdateParam {

    private String text;

    private Double point;

    private QuestionType questionType;

    private Long topic;

    private Long seniority;

    private QuestionStatus questionStatus;

    private List<AnswerAddUpdateParam> answers;

}
