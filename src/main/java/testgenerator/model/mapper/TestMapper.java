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
                 seniority, test.getQuestionStatus(), testStackDtos, testQuestionDtos);
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
        test.setQuestionStatus(param.getQuestionStatus());
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

    public static TestInfoDto testInfoDto(Test test) {
        return new TestInfoDto(test.getGivenTimeInMinutes(), test.getGivenTestStartDate(), test.getGivenTestEndDate(),
                test.getNumberOfOpenQuestions(), test.getNumberOfSingleChoiceTestQuestions(), test.getNumberOfMultipleChoiceTestQuestions());
    }

    public static TestStartDto testStartDto(Test test) {
        List<TestQuestionStartDto> testQuestionStartDtoList = test.getTestQuestions().stream().map(TestQuestionMapper::testQuestionStartDto).toList();

        return new TestStartDto(testQuestionStartDtoList);
    }
}
