package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CandidateUpdateParam {
    private String name;

    private String surname;

    private String email;

    private List<Long> testResults;
}
