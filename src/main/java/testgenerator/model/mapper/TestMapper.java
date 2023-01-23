package testgenerator.model.mapper;

import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Test;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.dto.TestDto;
import testgenerator.model.dto.TestQuestionDto;
import testgenerator.model.dto.TestStackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestAddParam;

import java.util.List;

public class TestMapper {

    public static TestDto testDto(Test test){
        SeniorityDto seniority = SeniorityMapper.seniorityDto(test.getSeniority());
        List<TestStackDto> testStackDtos = test.getTestStacks().stream().map(TestStackMapper::testStackDto).toList();
        List<TestQuestionDto> testQuestionDtos = test.getTestQuestions().stream().map(TestQuestionMapper::testQuestionDto).toList();

        return new TestDto(test.getId(), test.getGivenTime(), test.getNumberOfSingleChoiceTestQuestions(),
                test.getNumberOfMultipleChoiceTestQuestions(), test.getNumberOfOpenQuestions(), seniority, testStackDtos, testQuestionDtos);
    }

    public static Test paramToTest(Test test, TestAddParam param, Seniority seniority, List<TestStack> testStacks, List<TestQuestion> testQuestions) {
        test.setTestQuestions(testQuestions);
        test.setTestStacks(testStacks);
        test.setSeniority(seniority);
        test.setNumberOfMultipleChoiceTestQuestions(param.getNumberOfMultipleChoiceTestQuestions());
        test.setNumberOfSingleChoiceTestQuestions(param.getNumberOfSingleChoiceTestQuestions());
        test.setNumberOfOpenQuestions(param.getNumberOfOpenQuestions());
        test.setGivenTime(param.getGivenTime());
        test.setStatus(Status.ACTIVE);

        return test;
    }
}
