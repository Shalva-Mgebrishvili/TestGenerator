package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestStackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestStackMapper;
import testgenerator.model.params.TestStackParam;
import testgenerator.service.StackService;
import testgenerator.service.TestService;
import testgenerator.service.TestStackService;

@Service
@RequiredArgsConstructor
public class TestStackFacade {
    private final TestStackService service;
    private final TestService testService;

    private final StackService stackService;

    public TestStackDto findById(Long id) {
        TestStack testStack = service.findById(id, Status.ACTIVE);
        return TestStackMapper.testStackDto(testStack);
    }

    public Page<TestStackDto> findAll(Pageable pageable){
        Page<TestStack> allTestStacks = service.findAll(Status.ACTIVE, pageable);
        return allTestStacks.map(TestStackMapper::testStackDto);
    }

    public TestStackDto add(TestStackParam param) {
        Test test = testService.findById(param.getTest(), Status.ACTIVE);
        Stack stack = stackService.findById(param.getTest(), Status.ACTIVE);

        TestStack testStack = new TestStack(stack, test);
        testStack.setStatus(Status.ACTIVE);
        return TestStackMapper.testStackDto(service.add(testStack));
    }

    public TestStackDto update(Long id, TestStackParam param) {

        TestStack updateTestStack = service.findById(id,Status.ACTIVE);

        Test test = testService.findById(param.getTest(), Status.ACTIVE);
        Stack stack = stackService.findById(param.getTest(), Status.ACTIVE);

        updateTestStack.setTest(test);
        updateTestStack.setStack(stack);

        return TestStackMapper.testStackDto(service.add(updateTestStack));
    }

    public void deleteById(Long id) {
        TestStack testStack = service.findById(id, Status.ACTIVE);
        testStack.setStatus(Status.DEACTIVATED);
        service.add(testStack);
    }

}
