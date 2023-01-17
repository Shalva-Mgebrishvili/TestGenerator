package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.enums.QuestionType;

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

    private SeniorityDto seniority;

    private List<AnswerDto> answers;

}
