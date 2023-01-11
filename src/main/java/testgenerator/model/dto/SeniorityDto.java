package testgenerator.model.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Test;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeniorityDto {

    private Long id;

    private String seniorityLevel;

}
