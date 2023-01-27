package testgenerator.model.mapper;

import testgenerator.model.domain.Candidate;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.dto.UserShortDto;


public class CandidateMapper {

    public static CandidateDto candidateDto(Candidate candidate){
        UserShortDto user = UserMapper.userShortDto(candidate.getUser());

        return new CandidateDto(candidate.getId(), user, candidate.getOneTimeUsername(), candidate.getOneTimePassword());
    }
}
