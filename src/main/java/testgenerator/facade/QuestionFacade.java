package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.model.params.QuestionParam;
import testgenerator.service.QuestionService;


@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService service;

    public QuestionDto findById(Long id) throws Exception {
        Question question = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));

        return QuestionMapper.questionDto(question);
    }

    public Page<QuestionDto> findAll(Pageable pageable){
        Page<Question> allQuestions = service.findAll(Status.ACTIVE, pageable);

        return allQuestions.map(QuestionMapper::questionDto);
    }

    public QuestionDto add(QuestionParam param) {
        Question question = QuestionMapper.paramToQuestion(param);

        return QuestionMapper.questionDto(service.add(question));
    }

    public QuestionDto update(Long id, QuestionParam param) throws Exception {
        Question updateQuestion = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));

        Question question = QuestionMapper.updateQuestionWithParam(param, updateQuestion);

        return QuestionMapper.questionDto(service.add(question));
    }

    public void deleteById(Long id) throws Exception {
        Question question = service.findById(id, Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));
        question.setStatus(Status.DEACTIVATED);
        service.add(question);
    }

}
