package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.CandidateAnswerCorrectness;

import java.util.List;

public class TestQuestionMapper {

    public static TestQuestionDto testQuestionDto(TestQuestion testQuestion){

        QuestionForTestDto question = QuestionMapper.questionForTestDto(testQuestion.getQuestion());
        List<CandidateAnswerDto> candidateAnswerDtos = testQuestion.getCandidateAnswers().stream()
                .map(CandidateAnswerMapper::candidateAnswerDto).toList();

        return new TestQuestionDto(testQuestion.getId(), question, candidateAnswerDtos);
    }

    public static TestQuestionShortDto testQuestionShortDto(TestQuestion testQuestion){
        QuestionShortDto question = QuestionMapper.questionShortDto(testQuestion.getQuestion());

        CandidateAnswerCorrectness candidateAnswerCorrectness = CandidateAnswerCorrectness.INCORRECT;
        double maxPoint = testQuestion.getQuestion().getPoint();
        double candidatePoint = testQuestion.getCandidateAnswers().stream().mapToDouble(CandidateAnswer::getCandidatePoint).sum();

        if(maxPoint==candidatePoint) {
            candidateAnswerCorrectness = CandidateAnswerCorrectness.CORRECT;
        }else if(candidatePoint>0) {
            candidateAnswerCorrectness = CandidateAnswerCorrectness.PARTLY_CORRECT;
        }

        return new TestQuestionShortDto(question, candidateAnswerCorrectness);
    }

}
