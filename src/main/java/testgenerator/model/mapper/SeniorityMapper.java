package testgenerator.model.mapper;

import testgenerator.model.domain.Seniority;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.SeniorityParam;

public class SeniorityMapper {

    public static SeniorityDto seniorityDto(Seniority seniority) {
        return new SeniorityDto(seniority.getId(), seniority.getSeniorityLevel());
    }

    public static Seniority paramToSeniority(SeniorityParam param){
        Seniority seniority = new Seniority(param.getSeniorityLevel());
        seniority.setStatus(Status.ACTIVE);

       return seniority;
    }

    public static Seniority updateSeniorityWithParam(SeniorityParam param, Seniority seniority) {
        seniority.setSeniorityLevel(param.getSeniorityLevel());

        return seniority;
    }
}
