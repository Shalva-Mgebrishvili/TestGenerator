package testgenerator.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StackDto {

    private Long id;

    private String name;

    private List<TopicShortDto> topics;

}
