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
public class TestResultShortDto {

    private LocalDateTime candidateTestStartDate;

    private LocalDateTime candidateTestFinishDate;

    private Double totalPoint;

    private Double candidateScore;

    private TestShortDto test;
}
