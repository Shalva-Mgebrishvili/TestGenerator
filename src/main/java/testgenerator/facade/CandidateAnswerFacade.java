package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.model.params.CandidateAnswerAddParam;
import testgenerator.model.params.CandidateAnswerUpdateParam;
import testgenerator.service.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateAnswerFacade {

    private final CandidateAnswerService service;
    private final TestQuestionService testQuestionService;
    private final AnswerService answerService;
    private final CandidateService candidateService;
    private final TestService testService;


    public CandidateAnswerDto findById(Long id) {
        CandidateAnswer candidateAnswer = service.findById(id, Status.ACTIVE);

        return CandidateAnswerMapper.candidateAnswerDto(candidateAnswer);
    }

    public Page<CandidateAnswerDto> findAll(Pageable pageable){
        Page<CandidateAnswer> allCandidateAnswers = service.findAll(Status.ACTIVE, pageable);

        return allCandidateAnswers.map(CandidateAnswerMapper::candidateAnswerDto);
    }

    public CandidateAnswerDto add(CandidateAnswerAddParam param) {
        TestQuestion testQuestion = testQuestionService.findById(param.getTestQuestion(), Status.ACTIVE);
        Answer chosenAnswer = answerService.findById(param.getChosenAnswer(), Status.ACTIVE);
        Candidate candidate = candidateService.findById(param.getCandidate(), Status.ACTIVE);

        CandidateAnswer candidateAnswer = CandidateAnswerMapper.paramToCandidateAnswer(param, testQuestion, chosenAnswer, candidate);

        return CandidateAnswerMapper.candidateAnswerDto(service.add(candidateAnswer));
    }

    public CandidateAnswerDto update(Long id, CandidateAnswerUpdateParam param) {
        CandidateAnswer updateCandidateAnswer = service.findById(id,Status.ACTIVE);
        Test test = testService.findById(updateCandidateAnswer.getTestQuestion().getTest().getId(), Status.ACTIVE);
        List<TestResult> testResults = updateCandidateAnswer.getCandidate().getTestResults();
        Double pointBefore = updateCandidateAnswer.getCandidatePoint();
        Double pointAfter = param.getCandidatePoint();

        for(TestResult testResult: testResults) {
            if(testResult.getTest().equals(test)) testResult.setCandidateScore(testResult.getCandidateScore()-pointBefore+pointAfter);
        }

        updateCandidateAnswer.setCandidatePoint(param.getCandidatePoint());

        return CandidateAnswerMapper.candidateAnswerDto(service.add(updateCandidateAnswer));
    }

}
