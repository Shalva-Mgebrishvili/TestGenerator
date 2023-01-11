package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.Test;

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

    private List<Test> tests;

}
