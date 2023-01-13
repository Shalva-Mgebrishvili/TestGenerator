package testgenerator.model.mapper;

import testgenerator.model.domain.Topic;
import testgenerator.model.dto.TopicShortDto;

public class TopicMapper {

    public static TopicShortDto toShortDto(Topic topic) {
        return new TopicShortDto(topic.getId(), topic.getName());
    }
}
