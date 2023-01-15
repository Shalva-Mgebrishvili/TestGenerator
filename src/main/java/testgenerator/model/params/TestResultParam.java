package testgenerator.model.params;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TestResultParam {

    private LocalDateTime testStartDate;

    private LocalDateTime testFinishDate;

    private Integer timeNeeded;

    private Integer totalPoint;

    private Integer candidateScore;

    private Long test;

    private Long corrector;
}
