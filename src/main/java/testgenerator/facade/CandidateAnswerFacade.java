package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.model.params.CandidateAnswerParam;
import testgenerator.service.*;

@Service
@RequiredArgsConstructor
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

    public CandidateAnswerDto add(CandidateAnswerParam param) {
        TestQuestion testQuestion = testQuestionService.findById(param.getTestQuestion(), Status.ACTIVE);
        Answer chosenAnswer = answerService.findById(param.getChosenAnswer(), Status.ACTIVE);

        CandidateAnswer candidateAnswer = CandidateAnswerMapper.paramToCandidateAnswer(param, testQuestion, chosenAnswer);

        return CandidateAnswerMapper.candidateAnswerDto(service.add(candidateAnswer));
    }

    public CandidateAnswerDto update(Long id, CandidateAnswerParam param) {
        TestQuestion testQuestion = testQuestionService.findById(param.getTestQuestion(), Status.ACTIVE);
        Answer chosenAnswer = answerService.findById(param.getChosenAnswer(), Status.ACTIVE);

        CandidateAnswer updateCandidateAnswer = service.findById(id,Status.ACTIVE);

        CandidateAnswer candidateAnswer = CandidateAnswerMapper.updateCandidateAnswerWithParam(param, updateCandidateAnswer, testQuestion, chosenAnswer);

        return CandidateAnswerMapper.candidateAnswerDto(service.add(candidateAnswer));
    }

    public void deleteById(Long id) {
        CandidateAnswer candidateAnswer = service.findById(id, Status.ACTIVE);

        candidateAnswer.setStatus(Status.DEACTIVATED);

        service.add(candidateAnswer);
    }
}
