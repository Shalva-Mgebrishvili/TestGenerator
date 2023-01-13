package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private Long id;

    private String name;

    private StackDto stack;

}
