package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StackDto {

    private Long id;

    private String name;

}
