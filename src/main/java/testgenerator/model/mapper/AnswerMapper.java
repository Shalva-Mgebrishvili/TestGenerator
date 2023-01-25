package testgenerator.model.mapper;

import testgenerator.model.domain.Answer;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.AnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.AnswerAddParam;
import testgenerator.model.params.AnswerUpdateParam;

public class AnswerMapper {
    public static AnswerDto answerDto(Answer answer){
//        QuestionDto questionDto = QuestionMapper.questionDto(answer.getQuestion());

        return new AnswerDto(answer.getId(), answer.getAnswer(), answer.getIsCorrect());
    }

    public static Answer paramToAnswer(AnswerAddParam param, Question question) {
        Answer answer = new Answer();

        answer.setAnswer(param.getAnswer());
        answer.setQuestion(question);
        answer.setIsCorrect(param.getIsCorrect());
        answer.setStatus(Status.ACTIVE);

        return answer;
    }

    public static Answer updateAnswerWithParam(AnswerUpdateParam param, Answer answer) {
        answer.setAnswer(param.getAnswer());
        answer.setIsCorrect(param.getIsCorrect());

        return answer;
    }
}
