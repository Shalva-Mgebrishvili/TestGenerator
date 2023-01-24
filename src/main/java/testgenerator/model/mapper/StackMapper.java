package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.dto.TopicDto;

import java.util.ArrayList;
import java.util.List;

public class StackMapper {

    public static StackDto stackDto(Stack stack){
//        List<TopicDto> topics = stack.getTopics().stream().map(TopicMapper::topicDto).toList();

        return new StackDto(stack.getId(), stack.getName(), new ArrayList<>());
    }
}
