package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Long id;

    private Integer givenTime;

    private SeniorityDto seniority;

    private List<TestStackDto> testStacks;

    private List<TestQuestionDto> testQuestions;

}
