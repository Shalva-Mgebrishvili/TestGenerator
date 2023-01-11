package testgenerator.model.dto;

import lombok.*;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.TestQuestion;
import testgenerator.model.domain.TestStack;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Integer givenTime;

    private List<TestStack> testStacks;

    private Seniority seniority;

    private List<TestQuestion> testQuestions;

    private List<Candidate> candidates;
}
