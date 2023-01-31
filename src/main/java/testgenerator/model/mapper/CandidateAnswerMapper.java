package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.params.CandidateAnswerAddParam;
import testgenerator.service.AnswerService;
import testgenerator.service.TestQuestionService;

import java.util.List;

public class CandidateAnswerMapper {

    public static CandidateAnswerDto candidateAnswerDto(CandidateAnswer candidateAnswer){

//        TestQuestionDto testQuestion = TestQuestionMapper.testQuestionDto(candidateAnswer.getTestQuestion());
        AnswerDto chosenAnswer = AnswerMapper.answerDto(candidateAnswer.getChosenAnswer());
//        CandidateDto candidate = CandidateMapper.candidateDto(candidateAnswer.getCandidate());

        return new CandidateAnswerDto(candidateAnswer.getId(), candidateAnswer.getAnswer(),
                candidateAnswer.getCandidatePoint(), chosenAnswer);
    }

    public static CandidateAnswer paramToCandidateAnswer(CandidateAnswerAddParam param, TestQuestion testQuestion,
                                                         Answer chosenAnswer) {

        double candidatePoint=0.0;
        if(chosenAnswer != null) {
            double maxPoint=testQuestion.getQuestion().getPoint();
            List<Answer> correctList = testQuestion.getQuestion().getAnswers().stream().filter(Answer::getIsCorrect).toList();
            Integer size = correctList.size();

            for(Answer answer: correctList) {
                if (answer.getAnswer().equals(chosenAnswer.getAnswer())) {
                    candidatePoint = maxPoint / size;
                    break;
                }
            }
        }


        CandidateAnswer candidateAnswer = new CandidateAnswer(param.getAnswer(), candidatePoint, testQuestion, chosenAnswer);
        candidateAnswer.setStatus(Status.ACTIVE);

        return candidateAnswer;
    }
}
