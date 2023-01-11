package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.SeniorityEntity;
import testgenerator.model.domain.TestEntity;


import java.util.List;

@Data
@NoArgsConstructor
public class SeniorityDto {

    private Long seniorityId;

    private String seniorityLevel;

    private List<Question> questions;

    private List<TestEntity> tests;

    public SeniorityDto(SeniorityEntity seniority) {
        BeanUtils.copyProperties(seniority, this);
    }
}
