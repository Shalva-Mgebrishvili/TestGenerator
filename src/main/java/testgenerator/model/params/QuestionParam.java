package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
public class QuestionParam {

    private String text;

    private Integer point;

    private QuestionType questionType;

    private Long topic;

    private Long seniority;

    private List<Long> answers;

}
