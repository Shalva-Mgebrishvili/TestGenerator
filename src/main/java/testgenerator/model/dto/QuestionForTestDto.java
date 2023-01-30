package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testgenerator.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForTestDto {

    private Long id;

    private String text;

    private Double point;

    private QuestionType questionType;

    private TopicShortDto topic;

    private List<AnswerDto> answers;

}
