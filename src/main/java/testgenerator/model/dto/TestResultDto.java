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
public class TestResultDto {

    private Long id;

    private LocalDateTime testStartDate;

    private LocalDateTime testFinishDate;

    private Long timeNeeded;

    private Double totalPoint;

    private Double candidateScore;

    private TestDto test;

    private UserDto corrector;

    private CandidateDto candidate;

}
