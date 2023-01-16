package testgenerator.model.mapper;

import testgenerator.model.domain.Candidate;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.CandidateParam;

public class CandidateMapper {

    public static CandidateDto candidateDto(Candidate candidate){

        return new CandidateDto(candidate.getId(), candidate.getName(), candidate.getSurname(), candidate.getEmail());

    }

    public static Candidate paramToCandidate(CandidateParam param) {
        Candidate candidate = new Candidate();

        candidate.setName(param.getName());
        candidate.setSurname(param.getSurname());
        candidate.setEmail(param.getEmail());
        candidate.setStatus(Status.ACTIVE);
        return candidate;
    }

    public static Candidate updateCandidateWithParam(CandidateParam param, Candidate candidate) {
        candidate.setName(param.getName());
        candidate.setSurname(param.getSurname());
        candidate.setEmail(param.getEmail());
        return candidate;
    }
}
