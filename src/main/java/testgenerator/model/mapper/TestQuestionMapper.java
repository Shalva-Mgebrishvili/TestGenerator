package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;

import java.util.List;

public class TestQuestionMapper {

    public static TestQuestionDto testQuestionDto(TestQuestion testQuestion){

        QuestionDto question = QuestionMapper.questionDto(testQuestion.getQuestion());
        TestDto test = TestMapper.testDto(testQuestion.getTest());
        List<CandidateAnswerDto> candidateAnswerDtos = testQuestion.getCandidateAnswers().stream()
                .map(CandidateAnswerMapper::candidateAnswerDto).toList();

        return new TestQuestionDto(testQuestion.getId(), question, test, candidateAnswerDtos);
    }

}
