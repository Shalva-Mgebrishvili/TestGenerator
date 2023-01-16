package testgenerator.model.dto;

import lombok.*;

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
