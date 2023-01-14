package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Test;
import testgenerator.model.dto.TestDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestMapper;
import testgenerator.model.params.TestParam;
import testgenerator.service.TestService;

@Service
@RequiredArgsConstructor
public class TestFacade {

    private final TestService service;

    public TestDto findById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        return TestMapper.testDto(test);
    }

    public Page<TestDto> findAll(Pageable pageable) {
        Page<Test> allTests = service.findAll(Status.ACTIVE, pageable);

        return allTests.map(TestMapper::testDto);
    }

    public TestDto add(TestParam param) {
        Test test = TestMapper.paramToTest(param);

        return TestMapper.testDto(service.add(test));
    }

    public TestDto update(Long id, TestParam param) {
        Test updateQuestion = service.findById(id, Status.ACTIVE);

        Test test = TestMapper.updateTestWithParam(updateQuestion, param);

        return TestMapper.testDto(service.add(test));
    }

    public void deleteById(Long id) {
        Test test = service.findById(id, Status.ACTIVE);

        test.setStatus(Status.DEACTIVATED);

        service.add(test);
    }
}
