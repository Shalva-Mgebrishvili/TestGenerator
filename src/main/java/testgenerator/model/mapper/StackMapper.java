package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.dto.StackShortDto;

import java.util.ArrayList;

public class StackMapper {

    public static StackDto stackDto(Stack stack){

        return new StackDto(stack.getId(), stack.getName(), new ArrayList<>());
    }

    public static StackShortDto stackShortDto(Stack stack){
        return new StackShortDto(stack.getName());
    }
}
