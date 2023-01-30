package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;

public class TestStackMapper {
    public static TestStackDto testStackDto(TestStack testStack){
        StackDto stackDto = StackMapper.stackDto(testStack.getStack());
        TestDto testDto = TestMapper.testDto(testStack.getTest());

        return new TestStackDto(testStack.getId(), stackDto, testDto);
    }

    public static TestStackShortDto testStackShortDto(TestStack testStack){
        StackShortDto stackShortDto = StackMapper.stackShortDto(testStack.getStack());

        return new TestStackShortDto(stackShortDto);
    }

}
