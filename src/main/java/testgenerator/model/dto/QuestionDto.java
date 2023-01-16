package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.enums.QuestionType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private Long id;

    private String text;

    private Integer point;

    private QuestionType questionType;

    private TopicShortDto topic;

    //TopicShortDTO(Long id, String name), same with seniority

    private SeniorityDto seniority;

    private List<AnswerDto> answers;

}
