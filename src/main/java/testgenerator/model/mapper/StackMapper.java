package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.StackParam;

public class StackMapper {

    public static StackDto stackDto(Stack stack){
        return new StackDto(stack.getId(), stack.getName());
    }

    public static Stack paramToStack(StackParam param){
        Stack stack = new Stack(param.getName());
        stack.setStatus(Status.ACTIVE);

        return stack;
    }

    public static Stack updateStackWithParam(StackParam param, Stack stack) {
        stack.setName(param.getName());

        return stack;
    }
}
