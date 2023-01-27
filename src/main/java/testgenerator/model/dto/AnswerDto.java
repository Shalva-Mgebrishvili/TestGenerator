package testgenerator.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long id;

    private String answer;

    private Boolean isCorrect;

}
