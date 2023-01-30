package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TestSubmitParam {
    private Long testResultId;
    private LocalDateTime candidateTestStartDate;
    private LocalDateTime candidateTestFinishDate;
    private List<CandidateAnswerAddParam> candidateAnswerList;
}
