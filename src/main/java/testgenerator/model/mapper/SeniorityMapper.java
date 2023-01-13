package testgenerator.model.mapper;

import testgenerator.model.domain.Seniority;
import testgenerator.model.dto.SeniorityDto;

public class SeniorityMapper {

    public static SeniorityDto seniorityDto(Seniority seniority) {
        return new SeniorityDto(seniority.getId(), seniority.getSeniorityLevel());
    }
}
