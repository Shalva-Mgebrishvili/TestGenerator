package testgenerator.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private List<TestResultDto> testResults;

}
