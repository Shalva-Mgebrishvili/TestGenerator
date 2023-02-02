package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.enums.TestStatus;
import testgenerator.model.params.TestResultAddParam;
import testgenerator.model.params.TestSubmitParam;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TestResultMapper {

    public static TestResultDto testResultDto(TestResult testResult){
        TestDto test = TestMapper.testDto(testResult.getTest());
        UserShortDto user = UserMapper.userShortDto(testResult.getUser());
        List<UserShortDto> correctors = testResult.getCorrector().stream().map(UserMapper::userShortDto).toList();

        TestResultDto testResultDto = new TestResultDto();

        testResultDto.setId(testResult.getId());
        testResultDto.setCandidateTestFinishDate(testResult.getCandidateTestFinishDate());
        testResultDto.setCandidateTestStartDate(testResult.getCandidateTestStartDate());
        testResultDto.setTimeNeeded(testResult.getTimeNeeded());
        testResultDto.setTotalPoint(testResult.getTotalPoint());
        testResultDto.setCandidateScore(testResult.getCandidateScore());
        testResultDto.setTest(test);
        testResultDto.setCorrector(correctors);
        testResultDto.setUser(user);

        return testResultDto;
    }

    public static TestResult paramToTestResult(TestResultAddParam param, Test test, Candidate candidate) {
        TestResult testResult = new TestResult();

        Double totalPoint = test.getTestQuestions().stream().map(TestQuestion::getQuestion)
                .map(Question::getPoint).toList().stream().mapToDouble(Double::doubleValue).sum();

        testResult.setTotalPoint(totalPoint);
        testResult.setTotalPoint(param.getTotalPoint());
        testResult.setTest(test);
        testResult.setCandidate(candidate);
        testResult.setStatus(Status.ACTIVE);

        return testResult;
    }

    public static TestResult updateTestResultWithParam(TestResult testResult, TestSubmitParam param,
                                                       List<CandidateAnswer> candidateAnswerList) {

        Long timeNeeded = param.getCandidateTestStartDate().until(param.getCandidateTestFinishDate(), ChronoUnit.MINUTES);

        double candidateScore=0.0;

        for(CandidateAnswer candidateAnswer: candidateAnswerList) {
            candidateScore+=candidateAnswer.getCandidatePoint();
        }

        testResult.setCandidateTestFinishDate(param.getCandidateTestFinishDate());
        testResult.setCandidateTestStartDate(param.getCandidateTestStartDate());
        testResult.setTimeNeeded(timeNeeded);
        testResult.setCandidateScore(candidateScore);

        return testResult;
    }

    public static TestResult updateTestResultAfterCorrection(TestResult testResult,
                                                       List<CandidateAnswer> candidateAnswerList) {

        double candidateScore=0.0;

        for(CandidateAnswer candidateAnswer: candidateAnswerList) {
            candidateScore+=candidateAnswer.getCandidatePoint();
        }

        testResult.setCandidateScore(candidateScore);

        return testResult;
    }

    public static TestResultShortDto testResultShortDto(TestResult testResult){
        return new TestResultShortDto(testResult.getTotalPoint(), testResult.getCandidateScore());
    }

    public static TestResultByUserIdAndTestResultIdDto testResultByUserIdAndTestResultIdDto(TestResult testResult) {
        TestShortDto test = TestMapper.testShortDto(testResult.getTest());

        return new TestResultByUserIdAndTestResultIdDto(testResult.getCandidateTestStartDate(),
                testResult.getCandidateTestFinishDate(), testResult.getTimeNeeded(), test);

    }
}
