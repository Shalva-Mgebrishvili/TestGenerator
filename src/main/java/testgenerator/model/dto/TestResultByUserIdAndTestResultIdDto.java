package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultByUserIdAndTestResultIdDto {

    private LocalDateTime candidateTestStartDate;

    private LocalDateTime candidateTestFinishDate;

    private Long timeNeeded;

    private TestShortDto test;
}
