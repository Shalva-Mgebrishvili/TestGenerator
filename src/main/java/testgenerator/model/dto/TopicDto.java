package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.StackEntity;
import testgenerator.model.domain.TopicEntity;

import java.util.List;

@Data
@NoArgsConstructor
public class TopicDto {

    private Long topicId;

    private String name;

    private StackEntity stack;

    private List<Question> questions;

    public TopicDto(TopicEntity topic) {
        BeanUtils.copyProperties(topic, this);
    }
}
