package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.enums.QuestionType;

@Getter
@Setter
public class QuestionParam {

    private String text;
    private Integer point;
    private QuestionType questionType;
    private Topic topic;
    private Seniority seniority;

}
