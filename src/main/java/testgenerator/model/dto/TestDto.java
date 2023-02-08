package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.enums.QuestionStatus;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Long id;

    private Long givenTimeInMinutes;

    private LocalDateTime givenTestStartDate;

    private LocalDateTime givenTestEndDate;

    private Integer numberOfOpenQuestions;

    private Integer numberOfSingleChoiceTestQuestions;

    private Integer numberOfMultipleChoiceTestQuestions;

    private SeniorityDto seniority;

    private QuestionStatus questionStatus;

    private List<TestStackShortDto> testStacks;

    private List<TestQuestionDto> testQuestions;

}
