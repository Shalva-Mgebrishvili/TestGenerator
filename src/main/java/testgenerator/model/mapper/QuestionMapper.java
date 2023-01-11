package testgenerator.model.mapper;

import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;


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
}
