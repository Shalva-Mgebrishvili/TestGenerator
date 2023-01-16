package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Test;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.params.TestParam;
import testgenerator.service.SeniorityService;
import testgenerator.service.TestQuestionService;
import testgenerator.service.TestService;
import testgenerator.service.TestStackService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestFacade {

    private final TestService service;
    private final SeniorityService seniorityService;
    private final TestStackService testStackService;
    private final TestQuestionService testQuestionService;

    public TestDto findById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        return TestMapper.testDto(test);
    }

    public Page<TestDto> findAll(Pageable pageable) {
        Page<Test> allTests = service.findAll(Status.ACTIVE, pageable);

        return allTests.map(TestMapper::testDto);
    }

    public TestDto add(TestParam param) {
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<TestQuestion> testQuestionList = param.getTestQuestions().stream().map(t -> testQuestionService.findById(t, Status.ACTIVE)).toList();
        List<TestStack> testStackList = param.getTestStacks().stream().map(t -> testStackService.findById(t, Status.ACTIVE)).toList();

        Test test = new Test(param.getGivenTime(), seniority, testStackList, testQuestionList);
        test.setStatus(Status.ACTIVE);

        return TestMapper.testDto(service.add(test));
    }

    public TestDto update(Long id, TestParam param) {
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);
        List<TestQuestion> testQuestionList = param.getTestQuestions().stream().map(t -> testQuestionService.findById(t, Status.ACTIVE)).toList();
        List<TestStack> testStackList = param.getTestStacks().stream().map(t -> testStackService.findById(t, Status.ACTIVE)).toList();
        Test updateTest = service.findById(id, Status.ACTIVE);

        updateTest.setGivenTime(param.getGivenTime());
        updateTest.setSeniority(seniority);
        updateTest.setTestQuestions(testQuestionList);
        updateTest.setTestStacks(testStackList);

        return TestMapper.testDto(service.add(updateTest));
    }

    public void deleteById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        test.setStatus(Status.DEACTIVATED);

        service.add(test);
    }
}
