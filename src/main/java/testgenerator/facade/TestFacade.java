package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.mapper.TestResultMapper;
import testgenerator.model.params.TestAddParam;
import testgenerator.model.params.TestSubmitParam;
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
    private final StackService stackService;
    private final QuestionService questionService;
    private final TopicService topicService;
    private final TestResultService testResultService;
    private final TestQuestionService testQuestionService;
    private final AnswerService answerService;

//    public TestDto findById(Long id) {
//        Test test = service.findById(id, Status.ACTIVE);
//
//        return TestMapper.testDto(test);
//    }
//
//    public Page<TestDto> findAll(Pageable pageable) {
//        Page<Test> allTests = service.findAll(Status.ACTIVE, pageable);
//
//        return allTests.map(TestMapper::testDto);
//    }

    public TestDto add(TestAddParam param) {
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<Stack> stacks = param.getStacks().stream().map(s -> stackService.findById(s, Status.ACTIVE)).toList();
        List<Topic> topicList = param.getTopics().stream().map(t -> topicService.findById(t, Status.ACTIVE)).toList();
        Set<Question> questionList = new HashSet<>();

        topicList.forEach(topic -> {
            if(!stacks.contains(topic.getStack())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, topic + " topic does not belong to given stacks");
            }
        });

        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.OPEN_QUESTION, topicList, seniority, param.getNumberOfOpenQuestions(), questionList);
        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.SINGLE_CHOICE_TEST, topicList, seniority, param.getNumberOfSingleChoiceTestQuestions(), questionList);
        questionService.findQuestionsForTest(Status.ACTIVE, QuestionType.MULTIPLE_CHOICE_TEST, topicList, seniority, param.getNumberOfMultipleChoiceTestQuestions(), questionList);

        List<TestQuestion> testQuestionList = new ArrayList<>();

        Test test = new Test();
        service.add(TestMapper.paramToTest(test, param, seniority, new ArrayList<>(), new ArrayList<>()));

        for(Question question: questionList) {
            TestQuestion testQuestion = new TestQuestion(question, test, new ArrayList<>());
            testQuestion.setStatus(Status.ACTIVE);
            testQuestionList.add(testQuestion);
        }

        List<TestStack> testStackList = new ArrayList<>();

        for(Stack stack: stacks) {
            TestStack testStack = new TestStack(stack, test);
            testStack.setStatus(Status.ACTIVE);
            testStackList.add(testStack);
        }

        TestMapper.paramToTest(test, param, seniority, testStackList, testQuestionList);

        TestResult testResult = new TestResult(null, null, null,
                3.0, null, test, new ArrayList<>(), null,null);
        testResult.setStatus(Status.ACTIVE);
        testResultService.add(testResult);

        return TestMapper.testDto(service.add(test));
    }

    public void submit(TestSubmitParam param) {
        TestResult testResult = testResultService.findById(param.getTestResultId(), Status.ACTIVE);
        List<CandidateAnswer> candidateAnswerList = new ArrayList<>();

        param.getCandidateAnswerList().forEach(candidateAnswerAddParam -> {
            TestQuestion testQuestion = testQuestionService.findById(candidateAnswerAddParam.getTestQuestion(), Status.ACTIVE);
            Answer chosenAnswer = null;

            if(testQuestion.getQuestion().getQuestionType() != QuestionType.OPEN_QUESTION) {
                chosenAnswer = answerService.findById(candidateAnswerAddParam.getChosenAnswer(), Status.ACTIVE);
            }

            candidateAnswerList.add(CandidateAnswerMapper.paramToCandidateAnswer(candidateAnswerAddParam,
                    testQuestion, chosenAnswer));
        });

        TestResultMapper.updateTestResultWithParam(testResult, param, candidateAnswerList);
    }
}
