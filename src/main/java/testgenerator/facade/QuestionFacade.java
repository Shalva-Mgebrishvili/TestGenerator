package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.model.params.QuestionParam;
import testgenerator.service.QuestionService;
import testgenerator.utils.PageLimitChecker;


@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService service;

    public QuestionDto findById(Long id) throws Exception {
        Question question = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));

        return QuestionMapper.questionDto(question);
    }

    public Page<QuestionDto> findAll(Integer offset, Integer limit){
        Integer actualLimit = PageLimitChecker.checkPageLimit(limit);
        Pageable pageable = PageRequest.of(offset, actualLimit);
        Page<Question> allQuestions = service.findAll(Status.ACTIVE, pageable);

        return allQuestions.map(q-> QuestionMapper.questionDto(q));
    }

    public QuestionDto add(QuestionParam param) {
        Question question = new Question();

        question.setText(param.getText());
        question.setPoint(param.getPoint());
        question.setQuestionType(param.getQuestionType());
        question.setTopic(param.getTopic());
        question.setSeniority(param.getSeniority());
        question.setStatus(Status.ACTIVE);

        //Question question = QuestionMapper.toQuestionDb(param);

        return QuestionMapper.questionDto(service.add(question));
    }

    public QuestionDto update(Long id, QuestionParam param) throws Exception {
        Question updateQuestion = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));

        updateQuestion.setQuestionType(param.getQuestionType());
        updateQuestion.setPoint(param.getPoint());
        updateQuestion.setText(param.getText());
        updateQuestion.setTopic(param.getTopic());
        updateQuestion.setSeniority(param.getSeniority());

        return QuestionMapper.questionDto(service.add(updateQuestion));
    }

    public void deleteById(Long id) throws Exception {
        Question question = service.findById(id, Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));
        question.setStatus(Status.DEACTIVATED);
        service.add(question);
    }

}
