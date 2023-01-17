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
import testgenerator.service.*;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateAnswerFacade {

    private final CandidateAnswerService service;
    private final TestQuestionService testQuestionService;
    private final AnswerService answerService;

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

        CandidateAnswer candidateAnswer = CandidateAnswerMapper.paramToCandidateAnswer(param, testQuestion, chosenAnswer);

        return CandidateAnswerMapper.candidateAnswerDto(service.add(candidateAnswer));
    }

    public CandidateAnswerDto update(Long id, CandidateAnswerAddParam param) {
        CandidateAnswer updateCandidateAnswer = service.findById(id,Status.ACTIVE);
        updateCandidateAnswer.setCandidatePoint(param.getCandidatePoint());

        return CandidateAnswerMapper.candidateAnswerDto(service.add(updateCandidateAnswer));
    }

    public void deleteById(Long id) {
        CandidateAnswer candidateAnswer = service.findById(id, Status.ACTIVE);

        candidateAnswer.setStatus(Status.DEACTIVATED);

        service.add(candidateAnswer);
    }
}
