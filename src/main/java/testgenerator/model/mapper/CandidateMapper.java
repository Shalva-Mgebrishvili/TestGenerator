package testgenerator.model.mapper;

import testgenerator.model.domain.Candidate;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.dto.TestResultDto;
import testgenerator.model.dto.UserDto;

import java.util.List;

public class CandidateMapper {

    public static CandidateDto candidateDto(Candidate candidate){
        List<TestResultDto> testResultDtos = candidate.getTestResults().stream().map(TestResultMapper::testResultDto).toList();

        UserDto user = UserMapper.userDto(candidate.getUser());

        return new CandidateDto(candidate.getId(), testResultDtos, user);
    }
}
