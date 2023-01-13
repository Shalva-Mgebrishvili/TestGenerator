package testgenerator.model.mapper;

import testgenerator.model.domain.Seniority;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.params.SeniorityParam;

public class SeniorityMapper {

    public static SeniorityDto seniorityDto(Seniority seniority) {
        return new SeniorityDto(seniority.getId(), seniority.getSeniorityLevel());
    }

    public static Seniority paramToSeniority(SeniorityParam param){
       return new Seniority(param.getSeniorityLevel());
    }

    public static Seniority updateSeniorityWithParam(SeniorityParam param, Seniority seniority) {
        seniority.setSeniorityLevel(param.getSeniorityLevel());

        return seniority;
    }
}
