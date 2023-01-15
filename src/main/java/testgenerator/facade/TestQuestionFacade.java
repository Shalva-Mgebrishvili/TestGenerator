package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestQuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestQuestionMapper;
import testgenerator.model.params.TestQuestionParam;
import testgenerator.service.*;

@Service
@RequiredArgsConstructor
public class TestQuestionFacade {

    private final TestQuestionService service;
    private final QuestionService questionService;
    private final TestService testService;

    public TestQuestionDto findById(Long id) {
        TestQuestion testQuestion = service.findById(id, Status.ACTIVE);

        return TestQuestionMapper.testQuestionDto(testQuestion);
    }

    public Page<TestQuestionDto> findAll(Pageable pageable){
        Page<TestQuestion> allTestQuestions = service.findAll(Status.ACTIVE, pageable);

        return allTestQuestions.map(TestQuestionMapper::testQuestionDto);
    }

    public TestQuestionDto add(TestQuestionParam param) {
        Question question = questionService.findById(param.getQuestion(), Status.ACTIVE);
        Test test = testService.findById(param.getTest(), Status.ACTIVE);

        TestQuestion testQuestion = TestQuestionMapper.paramToTestQuestion(question, test);

        return TestQuestionMapper.testQuestionDto(service.add(testQuestion));
    }

    public TestQuestionDto update(Long id, TestQuestionParam param) {
        Question question = questionService.findById(param.getQuestion(), Status.ACTIVE);
        Test test = testService.findById(param.getTest(), Status.ACTIVE);

        TestQuestion updateTestQuestion = service.findById(id,Status.ACTIVE);
        updateTestQuestion.setQuestion(question);
        updateTestQuestion.setTest(test);

        return TestQuestionMapper.testQuestionDto(service.add(updateTestQuestion));
    }

    public void deleteById(Long id) {
        TestQuestion testQuestion = service.findById(id, Status.ACTIVE);
        testQuestion.setStatus(Status.DEACTIVATED);

        service.add(testQuestion);
    }
}
