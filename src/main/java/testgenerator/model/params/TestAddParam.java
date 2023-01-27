package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TestAddParam {

    private Long givenTimeInMinutes;

    private LocalDateTime givenTestStartDate;

    private LocalDateTime givenTestEndDate;

    private Integer numberOfOpenQuestions;

    private Integer numberOfSingleChoiceTestQuestions;

    private Integer numberOfMultipleChoiceTestQuestions;

    private Long seniority;

    private List<Long> stacks;

    private List<Long> topics;
}
