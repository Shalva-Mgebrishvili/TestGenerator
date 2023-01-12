package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.enums.QuestionType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private Long id;

    private String text;

    private LocalDateTime creationDate;

    private Integer point;

    private QuestionType questionType;

    private Long topic;

    private Long seniority;

}
