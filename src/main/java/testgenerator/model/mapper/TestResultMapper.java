package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestResultParam;

public class TestResultMapper {

    public static TestResultDto testResultDto(TestResult testResult){
        TestDto test = TestMapper.testDto(testResult.getTest());
        UserDto user = UserMapper.userDto(testResult.getCorrector());
        CandidateDto candidate = CandidateMapper.candidateDto(testResult.getCandidate());

        TestResultDto testResultDto = new TestResultDto();

        testResultDto.setId(testResult.getId());
        testResultDto.setTestFinishDate(testResult.getTestFinishDate());
        testResultDto.setTestStartDate(testResult.getTestStartDate());
        testResultDto.setTimeNeeded(testResult.getTimeNeeded());
        testResultDto.setTotalPoint(testResult.getTotalPoint());
        testResultDto.setCandidateScore(testResult.getCandidateScore());
        testResultDto.setTest(test);
        testResultDto.setCorrector(user);
        testResultDto.setCandidate(candidate);

        return testResultDto;
    }

    public static TestResult paramToTestResult(TestResultParam param, Test test, UserEntity user, Candidate candidate) {
        TestResult testResult = new TestResult();

        testResult.setTestFinishDate(param.getTestFinishDate());
        testResult.setTestStartDate(param.getTestStartDate());
        testResult.setTimeNeeded(param.getTimeNeeded());
        testResult.setTotalPoint(param.getTotalPoint());
        testResult.setCandidateScore(param.getCandidateScore());
        testResult.setTest(test);
        testResult.setCorrector(user);
        testResult.setCandidate(candidate);
        testResult.setStatus(Status.ACTIVE);

        return testResult;
    }

    public static TestResult updateTestResultWithParam(TestResultParam param, TestResult testResult,
                                                       Test test, UserEntity user, Candidate candidate) {

        testResult.setTestFinishDate(param.getTestFinishDate());
        testResult.setTestStartDate(param.getTestStartDate());
        testResult.setTimeNeeded(param.getTimeNeeded());
        testResult.setTotalPoint(param.getTotalPoint());
        testResult.setCandidateScore(param.getCandidateScore());
        testResult.setTest(test);
        testResult.setCorrector(user);
        testResult.setCandidate(candidate);

        return testResult;
    }
}
