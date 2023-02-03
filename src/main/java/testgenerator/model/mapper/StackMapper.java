package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.dto.StackShortDto;
import testgenerator.model.dto.TopicShortDto;

import java.util.List;

public class StackMapper {

    public static StackDto stackDto(Stack stack){
        List<TopicShortDto> topicShortDtos = stack.getTopics().stream().map(TopicMapper::toShortDto).toList();

        return new StackDto(stack.getId(), stack.getName(), topicShortDtos);
    }

    public static StackShortDto stackShortDto(Stack stack){
        return new StackShortDto(stack.getName());
    }
}
