package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.*;
import testgenerator.model.domain.Stack;
import testgenerator.model.dto.TestDto;
import testgenerator.model.dto.TestInfoDto;
import testgenerator.model.dto.TestStartDto;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.model.enums.TestStatus;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.mapper.TestResultMapper;
import testgenerator.model.params.TestAddParam;
import testgenerator.model.params.TestCorrectionParam;
import testgenerator.model.params.TestSubmitParam;
import testgenerator.service.*;

import java.util.*;

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
    private final CandidateAnswerService candidateAnswerService;
    private final UserService userService;
    private final CandidateService candidateService;
    private final KeycloakService keycloakService;

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

        test.setTestStatus(TestStatus.CREATED);

        return TestMapper.testDto(service.add(test));
    }

    @Transactional
    public void submit(TestSubmitParam param, Jwt jwt) {
        String email = (String) jwt.getClaims().get("email");
        UserEntity user = userService.findByEmail(email,Status.ACTIVE);
        List<TestResult> testResultList = user.getTestResults().stream().filter(testResult ->
        testResult.getTest().getTestStatus().equals(TestStatus.ACTIVE)).toList();

        if(testResultList.size() != 1)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

        List<CandidateAnswer> candidateAnswerList = new ArrayList<>();
        TestResult testResult = testResultList.get(0);

        param.getCandidateAnswerList().forEach(candidateAnswerAddParam -> {
            TestQuestion testQuestion = testQuestionService.findById(candidateAnswerAddParam.getTestQuestion(), Status.ACTIVE);
            Answer chosenAnswer = null;

            if(testQuestion.getQuestion().getQuestionType() != QuestionType.OPEN_QUESTION) {
                chosenAnswer = answerService.findById(candidateAnswerAddParam.getChosenAnswer(), Status.ACTIVE);
            }

            candidateAnswerList.add(candidateAnswerService.add(CandidateAnswerMapper.paramToCandidateAnswer(candidateAnswerAddParam,
                    testQuestion, chosenAnswer)));
        });

        Candidate candidate = testResult.getCandidate();
        candidate.setStatus(Status.DEACTIVATED);
        candidateService.add(candidate);
        keycloakService.deleteUserInKeycloak(candidate.getOneTimeUsername());

        testResult.getTest().setTestStatus(TestStatus.READY_FOR_CORRECTION);

        testResultService.add(TestResultMapper.updateTestResultWithParam(testResult, param, candidateAnswerList));
    }

    @Transactional
    public TestDto correction(TestCorrectionParam param) {
        TestResult testResult = testResultService.findById(param.getTestResultId(),Status.ACTIVE);
        UserEntity corrector = userService.findById(param.getCorrectorId(), Status.ACTIVE);
        Test test = testResult.getTest();

        List<Stack> correctorStack = corrector.getStacks();

        test.getTestStacks().stream().map(TestStack::getStack).toList().forEach(stack -> {
            if(!correctorStack.contains(stack))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "You do not have authority to correct test with given stack(s)");
        });

        if (test.getTestStatus() != TestStatus.READY_FOR_CORRECTION)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Test should have \"READY_FOR_CORRECTION\" as test status");

        if(!testResult.getCorrector().contains(corrector)) testResult.getCorrector().add(corrector);

        testResultService.add(testResult);

        List<CandidateAnswer> candidateAnswerList = new ArrayList<>();

        param.getCandidateAnswerUpdateParamList().forEach(candidateAnswerUpdateParam -> {
            CandidateAnswer candidateAnswer =
                    candidateAnswerService.findById(candidateAnswerUpdateParam.getId(), Status.ACTIVE);
            candidateAnswer.setCandidatePoint(candidateAnswerUpdateParam.getCandidatePoint());

            candidateAnswerService.add(candidateAnswer);
            candidateAnswerList.add(candidateAnswer);
        });

        testResult.setCandidateScore(testQuestionService.getCurrentPointSum(test));

        test.setTestStatus(param.getTestStatus());

        return TestMapper.testDto(service.add(test));
    }

    public TestInfoDto testInfoForCandidate(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        return TestMapper.testInfoDto(test);
    }

    public TestStartDto testStart(Long id) {
        Test test = service.findById(id, Status.ACTIVE);
        test.setTestStatus(TestStatus.ACTIVE);

        return TestMapper.testStartDto(test);
    }
}
