package testgenerator.model.mapper;

import testgenerator.model.domain.Test;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.dto.TestDto;
import testgenerator.model.dto.TestQuestionDto;
import testgenerator.model.dto.TestStackDto;

import java.util.List;

public class TestMapper {

    public static TestDto testDto(Test test){
        SeniorityDto seniority = SeniorityMapper.seniorityDto(test.getSeniority());
        List<TestStackDto> testStackDtos = test.getTestStacks().stream().map(TestStackMapper::testStackDto).toList();
        List<TestQuestionDto> testQuestionDtos = test.getTestQuestions().stream().map(TestQuestionMapper::testQuestionDto).toList();

        return new TestDto(test.getId(), test.getGivenTime(), seniority, testStackDtos, testQuestionDtos);
    }
}
