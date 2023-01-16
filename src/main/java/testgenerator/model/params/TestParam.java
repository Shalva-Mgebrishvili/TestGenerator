package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;

import java.util.List;

@Getter
@Setter
public class TestParam {

    private Integer givenTime;

    private Long seniority;

    private List<Long> testStacks;

    private List<Long> testQuestions;
}
