package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.TestResult;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateMapper;
import testgenerator.model.params.CandidateAddParam;
import testgenerator.model.params.CandidateUpdateParam;
import testgenerator.service.CandidateService;
import testgenerator.service.TestResultService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateFacade {

    private final CandidateService service;
    private final TestResultService testResultService;


    public CandidateDto findById(Long id) {
        Candidate candidate = service.findById(id, Status.ACTIVE);

        return CandidateMapper.candidateDto(candidate);
    }

    public Page<CandidateDto> findAll(Pageable pageable){
        Page<Candidate> allCandidates = service.findAll(Status.ACTIVE, pageable);

        return allCandidates.map(CandidateMapper::candidateDto);
    }

    public CandidateDto add(CandidateAddParam param) {
        Candidate candidate = new Candidate(param.getName(), param.getSurname(), param.getEmail(), new ArrayList<>());
        candidate.setStatus(Status.ACTIVE);

        return CandidateMapper.candidateDto(service.add(candidate));
    }

    public CandidateDto update(Long id, CandidateUpdateParam param) {
        List<TestResult> testResults = param.getTestResults().stream().map(t -> testResultService.findById(t, Status.ACTIVE)).toList();
        Candidate updateCandidate = service.findById(id,Status.ACTIVE);
        updateCandidate.setName(param.getName());
        updateCandidate.setSurname(param.getSurname());
        updateCandidate.setEmail(param.getEmail());
        updateCandidate.setTestResults(testResults);

        return CandidateMapper.candidateDto(service.add(updateCandidate));
    }

    public void deleteById(Long id) {
        Candidate candidate = service.findById(id, Status.ACTIVE);
        candidate.setStatus(Status.DEACTIVATED);

        service.add(candidate);
    }
}
