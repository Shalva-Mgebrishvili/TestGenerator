package testgenerator.model.mapper;

import testgenerator.model.domain.Test;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestParam;

public class TestMapper {

    public static TestDto testDto(Test test){
        return new TestDto(test.getId(), test.getGivenTime(), test.getSeniority());
    }

    public static Test paramToTest(TestParam param) {
        Test test = new Test();

        test.setSeniority(param.getSeniority());
        test.setGivenTime(param.getGivenTime());
        test.setStatus(Status.ACTIVE);

        return test;
    }

    public static Test updateTestWithParam(Test test, TestParam param) {
        test.setGivenTime(param.getGivenTime());
        test.setSeniority(param.getSeniority());

        return test;
    }
}
