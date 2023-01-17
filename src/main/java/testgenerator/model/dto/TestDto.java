package testgenerator.model.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Long id;

    private Long givenTime;

    private SeniorityDto seniority;

    private List<TestStackDto> testStacks;

    private List<TestQuestionDto> testQuestions;

}
