package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.params.TestAddParam;
import testgenerator.service.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TestFacade {

    private final TestService service;
    private final SeniorityService seniorityService;
    private final TestStackService testStackService;
    private final QuestionService questionService;
    private final TopicService topicService;

    public TestDto findById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        return TestMapper.testDto(test);
    }

    public Page<TestDto> findAll(Pageable pageable) {
        Page<Test> allTests = service.findAll(Status.ACTIVE, pageable);

        return allTests.map(TestMapper::testDto);
    }

    public TestDto add(TestAddParam param) {
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<TestStack> testStackList = param.getTestStacks().stream().map(t -> testStackService.findById(t, Status.ACTIVE)).toList();
        List<Topic> topicList = param.getTopics().stream().map(t -> topicService.findById(t, Status.ACTIVE)).toList();
        Set<Question> questionList = new HashSet<>();

        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.OPEN_QUESTION, topicList, seniority, param.getNumberOfOpenQuestions(), questionList);
        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.SINGLE_CHOICE_TEST, topicList, seniority, param.getNumberOfSingleChoiceTestQuestions(), questionList);
        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.MULTIPLE_CHOICE_TEST, topicList, seniority, param.getNumberOfMultipleChoiceTestQuestions(), questionList);

        List<TestQuestion> testQuestionList = new ArrayList<>();
        Test test = new Test();

        for(Question question: questionList) {
            TestQuestion testQuestion = new TestQuestion(question, test, new ArrayList<>());
            testQuestionList.add(testQuestion);
        }

        return TestMapper.testDto(service.add(TestMapper.paramToTest(test, param, seniority, testStackList, testQuestionList)));
    }
}
