package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.model.params.QuestionAddUpdateParam;
import testgenerator.service.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionFacade {

    private final QuestionService service;
    private final TopicService topicService;
    private final SeniorityService seniorityService;
    private final AnswerService answerService;

    public QuestionDto findById(Long id) {
        Question question = service.findById(id,Status.ACTIVE);

        return QuestionMapper.questionDto(question);
    }

    public Page<QuestionDto> findAll(Pageable pageable){
        Page<Question> allQuestions = service.findAll(Status.ACTIVE, pageable);

        return allQuestions.map(QuestionMapper::questionDto);
    }

    public QuestionDto add(QuestionAddUpdateParam param) {
        Topic topic = topicService.findById(param.getTopic(), Status.ACTIVE);
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<Answer> answers = new ArrayList<>();

        if(param.getQuestionType() != QuestionType.OPEN_QUESTION) {
            answers = param.getAnswers().stream().map(a -> answerService.findById(a, Status.ACTIVE)).toList();
        }

        Question question = new Question(param.getText(), param.getPoint(), param.getQuestionType(), topic, seniority, answers);
        question.setStatus(Status.ACTIVE);

        return QuestionMapper.questionDto(service.add(question));
    }

    public QuestionDto update(Long id, QuestionAddUpdateParam param) {
        Topic topic = topicService.findById(param.getTopic(), Status.ACTIVE);
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<Answer> answers = param.getAnswers().stream().map(a -> answerService.findById(a, Status.ACTIVE)).toList();

        Question updateQuestion = service.findById(id,Status.ACTIVE);

        updateQuestion.setText(param.getText());
        updateQuestion.setPoint(param.getPoint());
        updateQuestion.setQuestionType(param.getQuestionType());
        updateQuestion.setTopic(topic);
        updateQuestion.setSeniority(seniority);
        updateQuestion.setAnswers(answers);

        return QuestionMapper.questionDto(service.add(updateQuestion));
    }

    public void deleteById(Long id) {
        Question question = service.findById(id, Status.ACTIVE);

        question.setStatus(Status.DEACTIVATED);

        service.add(question);
    }

}
