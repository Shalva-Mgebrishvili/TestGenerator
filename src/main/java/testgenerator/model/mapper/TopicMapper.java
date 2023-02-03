package testgenerator.model.mapper;

import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.StackShortDto;
import testgenerator.model.dto.TopicDto;
import testgenerator.model.dto.TopicShortDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.TopicAddUpdateParam;

public class TopicMapper {

    public static TopicDto topicDto(Topic topic) {
        StackShortDto stackShortDto = StackMapper.stackShortDto(topic.getStack());

        return new TopicDto(topic.getId(), topic.getName(), stackShortDto);
    }

    public static Topic paramToTopic(TopicAddUpdateParam param, Stack stack) {
        Topic topic = new Topic();

        topic.setName(param.getName());
        topic.setStack(stack);
        topic.setStatus(Status.ACTIVE);

        return topic;
    }

    public static Topic updateTopicWithParam(TopicAddUpdateParam param, Topic topic, Stack stack) {
        topic.setName(param.getName());
        topic.setStack(stack);

        return topic;
    }

    public static TopicShortDto toShortDto(Topic topic) {
        return new TopicShortDto(topic.getId(), topic.getName());
    }
}
