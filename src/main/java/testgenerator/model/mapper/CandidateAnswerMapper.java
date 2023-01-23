package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.CandidateAnswerAddParam;

import java.util.List;

public class CandidateAnswerMapper {

    public static CandidateAnswerDto candidateAnswerDto(CandidateAnswer candidateAnswer){

        TestQuestionDto testQuestion = TestQuestionMapper.testQuestionDto(candidateAnswer.getTestQuestion());
        AnswerDto chosenAnswer = AnswerMapper.answerDto(candidateAnswer.getChosenAnswer());
        CandidateDto candidate = CandidateMapper.candidateDto(candidateAnswer.getCandidate());

        return new CandidateAnswerDto(candidateAnswer.getId(), candidateAnswer.getAnswer(),
                candidateAnswer.getCandidatePoint(), testQuestion, chosenAnswer, candidate);
    }

    public static CandidateAnswer paramToCandidateAnswer(CandidateAnswerAddParam param, TestQuestion testQuestion,
                                                         Answer chosenAnswer, Candidate candidate) {
        Double candidatePoint=0.0;
        Double maxPoint=testQuestion.getQuestion().getPoint();
        List<Answer> correctList = testQuestion.getQuestion().getAnswers().stream().filter(Answer::getIsCorrect).toList();
        Integer size = correctList.size();

        if(!correctList.isEmpty()){
            for(Answer answer: correctList) {
                if (answer.getAnswer().equals(chosenAnswer.getAnswer())) {
                    candidatePoint = maxPoint / size;
                    break;
                }
            }
        }

        CandidateAnswer candidateAnswer = new CandidateAnswer(param.getAnswer(), candidatePoint, testQuestion, chosenAnswer, candidate);
        candidateAnswer.setStatus(Status.ACTIVE);

        return candidateAnswer;
    }
}
