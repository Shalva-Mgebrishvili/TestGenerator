package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestResultAddParam;
import testgenerator.model.params.TestResultUpdateParam;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TestResultMapper {

    public static TestResultDto testResultDto(TestResult testResult){
        TestDto test = TestMapper.testDto(testResult.getTest());
//        UserDto user = UserMapper.userDto(testResult.getCorrector());
        CandidateDto candidate = CandidateMapper.candidateDto(testResult.getCandidate());

        TestResultDto testResultDto = new TestResultDto();

        testResultDto.setId(testResult.getId());
        testResultDto.setCandidateTestFinishDate(testResult.getCandidateTestFinishDate());
        testResultDto.setCandidateTestStartDate(testResult.getCandidateTestStartDate());
        testResultDto.setTimeNeeded(testResult.getTimeNeeded());
        testResultDto.setTotalPoint(testResult.getTotalPoint());
        testResultDto.setCandidateScore(testResult.getCandidateScore());
        testResultDto.setTest(test);
//        testResultDto.setCorrector(user);
        testResultDto.setCandidate(candidate);

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

    public static TestResult updateTestResultWithParam(TestResult testResult, TestResultUpdateParam param, UserEntity user, Test test) {
        Long timeNeeded = param.getCandidateTestStartDate().until(param.getCandidateTestFinishDate(), ChronoUnit.MINUTES);

        List<List<CandidateAnswer>> candidateAnswerList = test.getTestQuestions().stream().map(TestQuestion::getCandidateAnswers).toList();
        Double candidateScore=0.0;

        for(List<CandidateAnswer> candidateAnswers: candidateAnswerList) {
            candidateScore+=candidateAnswers.stream().map(CandidateAnswer::getCandidatePoint).toList().stream().mapToDouble(Double::doubleValue).sum();
        }

        testResult.setCandidateTestFinishDate(param.getCandidateTestFinishDate());
        testResult.setCandidateTestStartDate(param.getCandidateTestStartDate());
        testResult.setTimeNeeded(timeNeeded);
        testResult.setCandidateScore(candidateScore);
//        testResult.setCorrector(user);

        return testResult;
    }
}
