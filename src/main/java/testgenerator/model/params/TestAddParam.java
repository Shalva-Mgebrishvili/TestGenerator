package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestAddParam {

    private Long givenTime;

    private Long seniority;

    private List<Long> testStacks;

    private List<Long> testQuestions;
}
