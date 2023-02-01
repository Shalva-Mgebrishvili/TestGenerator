package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestInfoDto {

    private Long givenTimeInMinutes;
    private LocalDateTime givenTestStartDate;
    private LocalDateTime givenTestEndDate;
    private Integer numberOfOpenQuestions;
    private Integer numberOfSingleChoiceTestQuestions;
    private Integer numberOfMultipleChoiceTestQuestions;

}