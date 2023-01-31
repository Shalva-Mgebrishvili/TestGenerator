package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.enums.TestStatus;

import java.util.List;

@Getter
@Setter
public class TestCorrectionParam {
    private Long correctorId;
    private Long testResultId;
    private TestStatus testStatus;
    private List<CandidateAnswerUpdateParam> candidateAnswerUpdateParamList;

}
