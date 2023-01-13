package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.domain.Seniority;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Long id;

    private Integer givenTime;

    private Seniority seniority;

}
