package testgenerator.model.mapper;

import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.AnswerDto;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.AnswerParam;

public class AnswerMapper {
    public static AnswerDto answerDto(Answer answer){

        QuestionDto questionDto = QuestionMapper.questionDto(answer.getQuestion());

        return new AnswerDto(answer.getId(), answer.getAnswer(), answer.getIsCorrect(), questionDto);

    }

    public static Answer paramToAnswer(AnswerParam param, Question question) {
        Answer answer = new Answer();

        answer.setAnswer(param.getAnswer());
        answer.setQuestion(question);
        answer.setIsCorrect(param.getIsCorrect());
        answer.setStatus(Status.ACTIVE);

        return answer;
    }

    public static Answer updateAnswerWithParam(AnswerParam param, Answer answer, Question question) {
        answer.setAnswer(param.getAnswer());
        answer.setIsCorrect(param.getIsCorrect());
        answer.setQuestion(question);

        return answer;
    }
}
