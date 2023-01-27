package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Answer;
import testgenerator.model.dto.AnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.AnswerMapper;
import testgenerator.model.params.AnswerAddUpdateParam;
import testgenerator.service.AnswerService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerFacade {
    private final AnswerService service;

    public AnswerDto findById(Long id) {
        Answer answer = service.findById(id, Status.ACTIVE);

        return AnswerMapper.answerDto(answer);
    }

    public Page<AnswerDto> findAll(Pageable pageable){
        Page<Answer> allQuestions = service.findAll(Status.ACTIVE, pageable);

        return allQuestions.map(AnswerMapper::answerDto);
    }

    public AnswerDto update(Long id, AnswerAddUpdateParam param) {

        Answer updateAnswer = service.findById(id,Status.ACTIVE);

        Answer answer = AnswerMapper.updateAnswerWithParam(param, updateAnswer);

        return AnswerMapper.answerDto(service.add(answer));
    }

    public void deleteById(Long id) {
        Answer answer = service.findById(id,Status.ACTIVE);
        answer.setStatus(Status.DEACTIVATED);

        service.add(answer);
    }

}
