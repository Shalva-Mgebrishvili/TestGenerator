package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestResultDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestResultMapper;
import testgenerator.model.params.TestResultAddParam;
import testgenerator.model.params.TestResultUpdateParam;
import testgenerator.service.CandidateService;
import testgenerator.service.TestResultService;
import testgenerator.service.TestService;
import testgenerator.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultFacade {

    private final TestResultService service;
    private final TestService testService;
    private final UserService userService;
    private final CandidateService candidateService;

    public TestResultDto findById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);

        return TestResultMapper.testResultDto(testResult);
    }

    public Page<TestResultDto> findAll(Pageable pageable){
        Page<TestResult> allTestResults = service.findAll(Status.ACTIVE, pageable);

        return allTestResults.map(TestResultMapper::testResultDto);
    }

    public TestResultDto add(TestResultAddParam param) {
        Test test = testService.findById(param.getTest(), Status.ACTIVE);
        Candidate candidate = candidateService.findById(param.getCandidate(), Status.ACTIVE);

        TestResult testResult = TestResultMapper.paramToTestResult(param, test, null, candidate);

        return TestResultMapper.testResultDto(service.add(testResult));
    }

    public TestResultDto update(Long id, TestResultUpdateParam param) {
        UserEntity user = userService.findById(param.getCorrector(), Status.ACTIVE);

        TestResult updateTestResult = service.findById(id,Status.ACTIVE);
        updateTestResult.setCandidateScore(param.getCandidateScore());
        updateTestResult.setCorrector(user);

        return TestResultMapper.testResultDto(service.add(updateTestResult));
    }

    public void deleteById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);
        testResult.setStatus(Status.DEACTIVATED);

        service.add(testResult);
    }
}
