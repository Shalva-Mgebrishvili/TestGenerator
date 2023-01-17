package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestResultAddParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    public static TestResult paramToTestResult(TestResultAddParam param, Test test, UserEntity user, Candidate candidate) {
        Long timeNeeded = param.getTestStartDate().until(param.getTestFinishDate(), ChronoUnit.MINUTES);

        Double totalPoint = test.getTestQuestions().stream().map(TestQuestion::getQuestion)
                        .map(Question::getPoint).toList().stream().mapToDouble(Double::doubleValue).sum();

        List<List<CandidateAnswer>> candidateAnswerList = test.getTestQuestions().stream().map(TestQuestion::getCandidateAnswers).toList();
        Double candidateScore=0.0;

        for(List<CandidateAnswer> candidateAnswers: candidateAnswerList) {
            candidateScore+=candidateAnswers.stream().map(CandidateAnswer::getCandidatePoint).toList().stream().mapToDouble(Double::doubleValue).sum();
        }

        TestResult testResult = new TestResult();
        testResult.setTestFinishDate(param.getTestFinishDate());
        testResult.setTestStartDate(param.getTestStartDate());
        testResult.setTimeNeeded(timeNeeded);
        testResult.setTotalPoint(totalPoint);
        testResult.setCandidateScore(candidateScore);
        testResult.setTest(test);
        testResult.setCorrector(user);
        testResult.setCandidate(candidate);
        testResult.setStatus(Status.ACTIVE);

        return testResult;
    }
}
