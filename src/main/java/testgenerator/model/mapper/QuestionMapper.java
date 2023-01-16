package testgenerator.model.mapper;

import testgenerator.model.domain.Question;
import testgenerator.model.dto.AnswerDto;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.dto.TopicShortDto;

import java.util.List;


public class QuestionMapper {

    public static QuestionDto questionDto(Question question){

        TopicShortDto topic = TopicMapper.toShortDto(question.getTopic());
        SeniorityDto seniority = SeniorityMapper.seniorityDto(question.getSeniority());
        List<AnswerDto> answerDtos = question.getAnswers().stream().map(AnswerMapper::answerDto).toList();

        return new QuestionDto(question.getId(), question.getText(), question.getPoint(),
                question.getQuestionType(), topic, seniority, answerDtos);

    }

}
