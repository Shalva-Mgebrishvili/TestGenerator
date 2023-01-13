package testgenerator.model.mapper;

import testgenerator.model.domain.Test;
import testgenerator.model.dto.TestDto;

public class TestMapper {

    public static TestDto testDto(Test test){
        return new TestDto(test.getId(), test.getGivenTime(), test.getSeniority());
    }
}
