package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Candidate;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateMapper;
import testgenerator.model.params.CandidateParam;
import testgenerator.service.CandidateService;

@Service
@RequiredArgsConstructor
public class CandidateFacade {

    private final CandidateService service;

    public CandidateDto findById(Long id) {
        Candidate candidate = service.findById(id, Status.ACTIVE);

        return CandidateMapper.candidateDto(candidate);
    }

    public Page<CandidateDto> findAll(Pageable pageable){
        Page<Candidate> allCandidates = service.findAll(Status.ACTIVE, pageable);

        return allCandidates.map(CandidateMapper::candidateDto);
    }

    public CandidateDto add(CandidateParam param) {

        Candidate candidate = CandidateMapper.paramToCandidate(param);

        return CandidateMapper.candidateDto(service.add(candidate));
    }

    public CandidateDto update(Long id, CandidateParam param) {

        Candidate updateCandidate = service.findById(id,Status.ACTIVE);

        Candidate candidate = CandidateMapper.updateCandidateWithParam(param, updateCandidate);

        return CandidateMapper.candidateDto(service.add(candidate));
    }

    public void deleteById(Long id) {
        Candidate candidate = service.findById(id, Status.ACTIVE);
        candidate.setStatus(Status.DEACTIVATED);

        service.add(candidate);
    }
}
