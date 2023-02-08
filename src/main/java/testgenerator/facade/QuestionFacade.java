package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.AnswerMapper;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.model.params.AnswerAddUpdateParam;
import testgenerator.model.params.QuestionAddUpdateParam;
import testgenerator.service.*;

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

        Question question = QuestionMapper.paramToQuestion(param, answers, topic, seniority);

        if(param.getQuestionType() != QuestionType.OPEN_QUESTION) {
            for(AnswerAddUpdateParam answerParam: param.getAnswers()) {
                Answer currAnswer = AnswerMapper.paramToAnswer(answerParam, question);
                answers.add(currAnswer);
            }
        }

        question.setAnswers(answers);

        return QuestionMapper.questionDto(service.add(question));
    }

    public QuestionDto update(Long id, QuestionAddUpdateParam param) {
        Topic topic = topicService.findById(param.getTopic(), Status.ACTIVE);
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<Answer> newAnswers = new ArrayList<>();
        Question updateQuestion = service.findById(id,Status.ACTIVE);

        if(param.getQuestionType() != QuestionType.OPEN_QUESTION) {
            if(updateQuestion.getQuestionType() != QuestionType.OPEN_QUESTION) {
                List<Answer> prevAnswers = updateQuestion.getAnswers().stream().map(a -> answerService.findById(a.getId(), Status.ACTIVE)).toList();

                for(Answer answer: prevAnswers) {
                    answer.setStatus(Status.DEACTIVATED);
                }

                for(AnswerAddUpdateParam answerAddUpdateParam: param.getAnswers()) {
                    Answer answer = AnswerMapper.paramToAnswer(answerAddUpdateParam, updateQuestion);

                    newAnswers.add(answer);
                }
            }
        }

        updateQuestion.setText(param.getText());
        updateQuestion.setPoint(param.getPoint());
        updateQuestion.setQuestionType(param.getQuestionType());
        updateQuestion.setTopic(topic);
        updateQuestion.setSeniority(seniority);
        updateQuestion.setAnswers(newAnswers);
        updateQuestion.setQuestionStatus(param.getQuestionStatus());

        return QuestionMapper.questionDto(service.add(updateQuestion));
    }

    public void deleteById(Long id) {
        Question question = service.findById(id, Status.ACTIVE);

        question.setStatus(Status.DEACTIVATED);

        service.add(question);
    }

}
