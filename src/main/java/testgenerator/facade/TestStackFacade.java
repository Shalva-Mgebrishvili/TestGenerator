package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestStackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestStackMapper;
import testgenerator.model.params.TestStackAddParam;
import testgenerator.service.StackService;
import testgenerator.service.TestService;
import testgenerator.service.TestStackService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    public TestStackDto add(TestStackAddParam param) {
        Test test = testService.findById(param.getTest(), Status.ACTIVE);
        Stack stack = stackService.findById(param.getTest(), Status.ACTIVE);

        TestStack testStack = new TestStack(stack, test);
        testStack.setStatus(Status.ACTIVE);
        return TestStackMapper.testStackDto(service.add(testStack));
    }

}
