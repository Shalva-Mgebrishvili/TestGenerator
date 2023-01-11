package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testgenerator.model.domain.Test;
import testgenerator.model.domain.UserEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDto {

    private Integer timeNeeded;

    private Integer totalPoint;

    private Integer candidateScore;

    private Test test;

    private UserEntity corrector;
}
