package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.CandidateAnswerParam;

public class CandidateAnswerMapper {

    public static CandidateAnswerDto candidateAnswerDto(CandidateAnswer candidateAnswer){

        TestQuestionDto testQuestion = TestQuestionMapper.testQuestionDto(candidateAnswer.getTestQuestion());
        AnswerDto chosenAnswer = AnswerMapper.answerDto(candidateAnswer.getChosenAnswer());

        CandidateAnswerDto candidateAnswerDto = new CandidateAnswerDto(candidateAnswer.getId(), candidateAnswer.getAnswer(),
                candidateAnswer.getCandidatePoint(), testQuestion.getId(), chosenAnswer.getId());

        return candidateAnswerDto;
    }

    public static CandidateAnswer paramToCandidateAnswer(CandidateAnswerParam param, TestQuestion testQuestion, Answer chosenAnswer) {
        CandidateAnswer candidateAnswer = new CandidateAnswer(param.getAnswer(), param.getCandidatePoint(), testQuestion, chosenAnswer);
        candidateAnswer.setStatus(Status.ACTIVE);

        return candidateAnswer;
    }

    public static CandidateAnswer updateCandidateAnswerWithParam(CandidateAnswerParam param, CandidateAnswer candidateAnswer,
                                                                 TestQuestion testQuestion, Answer chosenAnswer) {

        candidateAnswer.setAnswer(param.getAnswer());
        candidateAnswer.setChosenAnswer(chosenAnswer);
        candidateAnswer.setCandidatePoint(param.getCandidatePoint());
        candidateAnswer.setTestQuestion(testQuestion);

        return candidateAnswer;
    }
}
