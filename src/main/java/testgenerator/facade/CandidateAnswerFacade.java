package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.model.params.CandidateAnswerAddParam;
import testgenerator.service.AnswerService;
import testgenerator.service.CandidateAnswerService;
import testgenerator.service.CandidateService;
import testgenerator.service.TestQuestionService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateAnswerFacade {

    private final CandidateAnswerService service;
    private final TestQuestionService testQuestionService;
    private final AnswerService answerService;
    private final CandidateService candidateService;

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


    public void deleteById(Long id) {
        CandidateAnswer candidateAnswer = service.findById(id, Status.ACTIVE);

        candidateAnswer.setStatus(Status.DEACTIVATED);

        service.add(candidateAnswer);
    }

}
