package testgenerator.model.mapper;

import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.AnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.AnswerAddUpdateParam;

public class AnswerMapper {
    public static AnswerDto answerDto(Answer answer){
        return new AnswerDto(answer.getId(), answer.getAnswer(), answer.getIsCorrect());
    }

    public static Answer paramToAnswer(AnswerAddUpdateParam param, Question question) {
        Answer answer = new Answer();

        answer.setAnswer(param.getAnswer());
        answer.setIsCorrect(param.getIsCorrect());
        answer.setQuestion(question);
        answer.setStatus(Status.ACTIVE);

        return answer;
    }

    public static Answer updateAnswerWithParam(AnswerAddUpdateParam param, Answer answer) {
        answer.setAnswer(param.getAnswer());
        answer.setIsCorrect(param.getIsCorrect());

        return answer;
    }
}
