package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TestResultUpdateParam {

    private LocalDateTime candidateTestStartDate;
    private LocalDateTime candidateTestFinishDate;
    private Long timeNeeded;
    private Double candidateScore;
    private Long corrector;

}
