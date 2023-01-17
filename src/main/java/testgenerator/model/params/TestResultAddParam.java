package testgenerator.model.params;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TestResultAddParam {

    private LocalDateTime testStartDate;

    private LocalDateTime testFinishDate;

    private Long test;

    private Long candidate;
}
