package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.CandidateEntity;
import testgenerator.model.domain.TestEntity;

import java.util.List;

@Data
@NoArgsConstructor
public class CandidateDto {

    private Long candidateId;

    private String name;

    private String surname;

    private String email;

    private List<TestEntity> tests;

    public CandidateDto(CandidateEntity candidate) {
        BeanUtils.copyProperties(candidate, this);
    }
}
