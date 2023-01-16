package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.CandidateAnswerAddParam;

public class CandidateAnswerMapper {

    public static CandidateAnswerDto candidateAnswerDto(CandidateAnswer candidateAnswer){

        TestQuestionDto testQuestion = TestQuestionMapper.testQuestionDto(candidateAnswer.getTestQuestion());
        AnswerDto chosenAnswer = AnswerMapper.answerDto(candidateAnswer.getChosenAnswer());

        CandidateAnswerDto candidateAnswerDto = new CandidateAnswerDto(candidateAnswer.getId(), candidateAnswer.getAnswer(),
                candidateAnswer.getCandidatePoint(), testQuestion.getId(), chosenAnswer.getId());

        return candidateAnswerDto;
    }

    public static CandidateAnswer paramToCandidateAnswer(CandidateAnswerAddParam param, TestQuestion testQuestion, Answer chosenAnswer) {
        CandidateAnswer candidateAnswer = new CandidateAnswer(param.getAnswer(), param.getCandidatePoint(), testQuestion, chosenAnswer);
        candidateAnswer.setStatus(Status.ACTIVE);

        return candidateAnswer;
    }
}
