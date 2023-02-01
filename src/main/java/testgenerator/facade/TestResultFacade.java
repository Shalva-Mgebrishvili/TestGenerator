package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestResultDto;
import testgenerator.model.dto.TestResultShortDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestResultMapper;
import testgenerator.model.params.TestResultUpdateParam;
import testgenerator.service.TestResultService;
import testgenerator.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultFacade {

    private final TestResultService service;
    private final UserService userService;

    public TestResultDto findById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);

        return TestResultMapper.testResultDto(testResult);
    }

    public Page<TestResultDto> findAll(Pageable pageable){
        Page<TestResult> allTestResults = service.findAll(Status.ACTIVE, pageable);

        return allTestResults.map(TestResultMapper::testResultDto);
    }

    public Page<TestResultShortDto> findAllByUserId(Long userId, Pageable pageable) {
        userService.findById(userId, Status.ACTIVE);

        Page<TestResult> allUserTestResults = service.findAllByUserId(Status.ACTIVE, userId, pageable);

        return allUserTestResults.map(TestResultMapper::testResultShortDto);
    }

//    public TestResultDto add(TestResultAddParam param) {
//        Test test = testService.findById(param.getTest(), Status.ACTIVE);
//        Candidate candidate = candidateService.findById(param.getCandidate(), Status.ACTIVE);
//
//        TestResult testResult = TestResultMapper.paramToTestResult(param, test, candidate);
//
//        return TestResultMapper.testResultDto(service.add(testResult));
//    }

    public TestResultDto update(Long id, TestResultUpdateParam param) {
        UserEntity corrector = userService.findById(param.getCorrector(), Status.ACTIVE);

        TestResult updateTestResult = service.findById(id,Status.ACTIVE);
        updateTestResult.getCorrector().add(corrector);

        return TestResultMapper.testResultDto(service.add(updateTestResult));
    }

    public void deleteById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);
        testResult.setStatus(Status.DEACTIVATED);

        service.add(testResult);
    }
}
