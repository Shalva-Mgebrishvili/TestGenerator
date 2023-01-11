package testgenerator.model.mapper;

import testgenerator.model.domain.AnswerEntity;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionDto questionDto(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        return questionDto;
    }
}
