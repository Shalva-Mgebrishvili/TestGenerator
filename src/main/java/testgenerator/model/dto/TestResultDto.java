package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDto {

    private Long id;

    private LocalDateTime candidateTestStartDate;

    private LocalDateTime candidateTestFinishDate;

    private Long timeNeeded;

    private Double totalPoint;

    private Double candidateScore;

    private TestDto test;

    private List<UserShortDto> reviewer;

    private UserShortDto user;

}
