package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.dto.StackShortDto;

import java.util.ArrayList;

public class StackMapper {

    public static StackDto stackDto(Stack stack){
//        List<TopicDto> topics = stack.getTopics().stream().map(TopicMapper::topicDto).toList();

        return new StackDto(stack.getId(), stack.getName(), new ArrayList<>());
    }

    public static StackShortDto stackShortDto(Stack stack){
        return new StackShortDto(stack.getName());
    }
}
