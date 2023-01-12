package testgenerator.model.mapper;

import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.QuestionParam;


public class QuestionMapper {

    public static QuestionDto questionDto(Question question){
        QuestionDto questionDto = new QuestionDto();

        questionDto.setId(question.getId());
        questionDto.setQuestionType(question.getQuestionType());
        questionDto.setText(question.getText());
        questionDto.setPoint(question.getPoint());
        questionDto.setSeniority(question.getSeniority().getId());
        questionDto.setTopic(question.getTopic().getId());

        return questionDto;
    }

    public static Question paramToQuestion(QuestionParam param) {
        Question question = new Question();

        question.setText(param.getText());
        question.setPoint(param.getPoint());
        question.setQuestionType(param.getQuestionType());
        question.setTopic(param.getTopic());
        question.setSeniority(param.getSeniority());
        question.setStatus(Status.ACTIVE);

        return question;
    }

    public static Question updateQuestionWithParam(QuestionParam param, Question question) {
        question.setText(param.getText());
        question.setPoint(param.getPoint());
        question.setQuestionType(param.getQuestionType());
        question.setTopic(param.getTopic());
        question.setSeniority(param.getSeniority());

        return question;
    }
}
