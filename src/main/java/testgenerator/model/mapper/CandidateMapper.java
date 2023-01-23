package testgenerator.model.mapper;

import testgenerator.model.domain.Candidate;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.dto.TestResultDto;

import java.util.List;

public class CandidateMapper {

    public static CandidateDto candidateDto(Candidate candidate){

        return new CandidateDto(candidate.getId(), candidate.getName(), candidate.getSurname(), candidate.getEmail());
    }
}
