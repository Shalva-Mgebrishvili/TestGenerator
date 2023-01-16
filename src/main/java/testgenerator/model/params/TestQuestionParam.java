package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestQuestionParam {

    private Long question;

    private Long test;

    private List<Long> candidateAnswers;

}
