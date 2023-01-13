package testgenerator.model.mapper;

import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.dto.TopicShortDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.QuestionParam;


public class QuestionMapper {

    public static QuestionDto questionDto(Question question){

        TopicShortDto topic = TopicMapper.toShortDto(question.getTopic());
        SeniorityDto seniority = SeniorityMapper.seniorityDto(question.getSeniority());

        QuestionDto questionDto = new QuestionDto();

        questionDto.setId(question.getId());
        questionDto.setQuestionType(question.getQuestionType());
        questionDto.setText(question.getText());
        questionDto.setPoint(question.getPoint());
        questionDto.setSeniority(seniority);
        questionDto.setTopic(topic);

        return questionDto;
    }

    public static Question paramToQuestion(QuestionParam param, Topic topic, Seniority seniority) {
        Question question = new Question();

        question.setText(param.getText());
        question.setPoint(param.getPoint());
        question.setQuestionType(param.getQuestionType());
        question.setTopic(topic);
        question.setSeniority(seniority);
        question.setStatus(Status.ACTIVE);

        return question;
    }

    public static Question updateQuestionWithParam(QuestionParam param, Question question, Topic topic, Seniority seniority) {
        question.setText(param.getText());
        question.setPoint(param.getPoint());
        question.setQuestionType(param.getQuestionType());
        question.setTopic(topic);
        question.setSeniority(seniority);

        return question;
    }
}
