package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TestQuestionParam;

public class TestQuestionMapper {

    public static TestQuestionDto testQuestionDto(TestQuestion testQuestion){

        QuestionDto question = QuestionMapper.questionDto(testQuestion.getQuestion());
        TestDto test = TestMapper.testDto(testQuestion.getTest());

        return new TestQuestionDto(testQuestion.getId(), question, test);
    }

    public static TestQuestion paramToTestQuestion(Question question, Test test) {
        TestQuestion testQuestion = new TestQuestion();

        testQuestion.setQuestion(question);
        testQuestion.setTest(test);
        testQuestion.setStatus(Status.ACTIVE);

        return testQuestion;
    }

}
