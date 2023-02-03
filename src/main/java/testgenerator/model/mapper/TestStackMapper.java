package testgenerator.model.mapper;

import testgenerator.model.domain.*;
import testgenerator.model.dto.*;

public class TestStackMapper {
    public static TestStackDto testStackDto(TestStack testStack){
        StackShortDto stackShortDto = StackMapper.stackShortDto(testStack.getStack());

        return new TestStackDto(testStack.getId(), stackShortDto, testStack.getTest().getId());
    }

    public static TestStackShortDto testStackShortDto(TestStack testStack){
        StackShortDto stackShortDto = StackMapper.stackShortDto(testStack.getStack());

        return new TestStackShortDto(stackShortDto);
    }

}
