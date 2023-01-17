package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.params.TestAddParam;
import testgenerator.model.params.TestUpdateParam;
import testgenerator.service.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestFacade {

    private final TestService service;
    private final SeniorityService seniorityService;
    private final TestStackService testStackService;
    private final TestQuestionService testQuestionService;
    private final TestResultService testResultService;

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
        List<TestQuestion> testQuestionList = param.getTestQuestions().stream().map(t -> testQuestionService.findById(t, Status.ACTIVE)).toList();
        List<TestStack> testStackList = param.getTestStacks().stream().map(t -> testStackService.findById(t, Status.ACTIVE)).toList();

        Test test = new Test(param.getGivenTime(), seniority, testStackList, testQuestionList);
        test.setStatus(Status.ACTIVE);

        return TestMapper.testDto(service.add(test));
    }

    public TestDto update(Long id, TestUpdateParam param) {
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);

        Test updateTest = service.findById(id, Status.ACTIVE);

        updateTest.setSeniority(seniority);

        return TestMapper.testDto(service.add(updateTest));
    }

    public void deleteById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        test.setStatus(Status.DEACTIVATED);

        service.add(test);
    }
}
