package testgenerator.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.StackEntity;
import testgenerator.model.domain.TopicEntity;

import java.util.List;

@Data
@NoArgsConstructor
public class StackDto {

    private Long stackId;

    private String name;

    private List<TopicEntity> topics;

    public StackDto(StackEntity stack) {
        BeanUtils.copyProperties(stack, this);
    }
}
