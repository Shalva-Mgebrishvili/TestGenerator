package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
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

    //param
    private List<Long> answers;

}
