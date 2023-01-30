package testgenerator.model.mapper;

import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Test;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestAddParam;

import java.util.List;

public class TestMapper {

    public static TestDto testDto(Test test){
        SeniorityDto seniority = SeniorityMapper.seniorityDto(test.getSeniority());
        List<TestStackShortDto> testStackDtos = test.getTestStacks().stream().map(TestStackMapper::testStackShortDto).toList();
        List<TestQuestionDto> testQuestionDtos = test.getTestQuestions().stream().map(TestQuestionMapper::testQuestionDto).toList();

        return new TestDto(test.getId(), test.getGivenTimeInMinutes(), test.getGivenTestStartDate(), test.getGivenTestEndDate(),
                test.getNumberOfOpenQuestions(),test.getNumberOfSingleChoiceTestQuestions(), test.getNumberOfMultipleChoiceTestQuestions(),
                 seniority, testStackDtos, testQuestionDtos);
    }

    public static Test paramToTest(Test test, TestAddParam param, Seniority seniority, List<TestStack> testStacks, List<TestQuestion> testQuestions) {
        test.setTestQuestions(testQuestions);
        test.setTestStacks(testStacks);
        test.setSeniority(seniority);
        test.setGivenTestStartDate(param.getGivenTestStartDate());
        test.setGivenTestEndDate(param.getGivenTestEndDate());
        test.setNumberOfMultipleChoiceTestQuestions(param.getNumberOfMultipleChoiceTestQuestions());
        test.setNumberOfSingleChoiceTestQuestions(param.getNumberOfSingleChoiceTestQuestions());
        test.setNumberOfOpenQuestions(param.getNumberOfOpenQuestions());
        test.setGivenTimeInMinutes(param.getGivenTimeInMinutes());
        test.setStatus(Status.ACTIVE);

        return test;
    }

    public static TestShortDto testShortDto(Test test){
        SeniorityDto seniority = SeniorityMapper.seniorityDto(test.getSeniority());
        List<TestStackShortDto> testStackShortDtos = test.getTestStacks().stream().map(TestStackMapper::testStackShortDto).toList();
        List<TestQuestionShortDto> testQuestionShortDtos = test.getTestQuestions().stream().map(TestQuestionMapper::testQuestionShortDto).toList();

        return new TestShortDto(test.getGivenTimeInMinutes(),
                seniority, testStackShortDtos, testQuestionShortDtos);
    }
}
