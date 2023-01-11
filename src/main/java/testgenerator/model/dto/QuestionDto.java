package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.*;
import testgenerator.model.enums.QuestionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
