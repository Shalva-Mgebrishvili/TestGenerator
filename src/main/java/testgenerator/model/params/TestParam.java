package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.domain.Seniority;

@Getter
@Setter
public class TestParam {

    private Integer givenTime;

    private Seniority seniority;
}
