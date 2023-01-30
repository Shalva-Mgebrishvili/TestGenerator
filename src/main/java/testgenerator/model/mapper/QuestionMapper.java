package testgenerator.model.mapper;

import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.*;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.params.QuestionAddUpdateParam;

import java.util.ArrayList;
import java.util.List;


public class QuestionMapper {

    public static QuestionDto questionDto(Question question){

        TopicShortDto topic = TopicMapper.toShortDto(question.getTopic());
        SeniorityDto seniority = SeniorityMapper.seniorityDto(question.getSeniority());
        List<AnswerDto> answerDtos = new ArrayList<>();

        if(question.getQuestionType() != QuestionType.OPEN_QUESTION) {
            answerDtos = question.getAnswers().stream().filter(a -> a.getStatus()==Status.ACTIVE).map(AnswerMapper::answerDto).toList();
        }

        return new QuestionDto(question.getId(), question.getText(), question.getPoint(),
                question.getQuestionType(), topic, seniority, answerDtos);
    }

    public static Question paramToQuestion(QuestionAddUpdateParam param, List<Answer> answers, Topic topic, Seniority seniority) {

        Question question = new Question();

        question.setQuestionType(param.getQuestionType());
        question.setText(param.getText());
        question.setAnswers(answers);
        question.setSeniority(seniority);
        question.setPoint(param.getPoint());
        question.setTopic(topic);
        question.setStatus(Status.ACTIVE);

        return question;
    }

    public static QuestionShortDto questionShortDto(Question question){
        return new QuestionShortDto(question.getText());
    }

    public static QuestionForTestDto questionForTestDto(Question question){
        TopicShortDto topic = TopicMapper.toShortDto(question.getTopic());
        List<AnswerDto> answerDtos = new ArrayList<>();

        if(question.getQuestionType() != QuestionType.OPEN_QUESTION) {
            answerDtos = question.getAnswers().stream().filter(a -> a.getStatus()==Status.ACTIVE).map(AnswerMapper::answerDto).toList();
        }

        return new QuestionForTestDto(question.getId(), question.getText(), question.getPoint(),
                question.getQuestionType(), topic, answerDtos);
    }

}
