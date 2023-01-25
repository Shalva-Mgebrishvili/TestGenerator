package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.TestResult;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateMapper;
import testgenerator.model.params.CandidateUpdateParam;
import testgenerator.service.CandidateService;
import testgenerator.service.TestResultService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateFacade {

    private final CandidateService service;
    private final TestResultService testResultService;


//    public CandidateDto findById(Long id) {
//        Candidate candidate = service.findById(id, Status.ACTIVE);
//
//        return CandidateMapper.candidateDto(candidate);
//    }
//
//    public Page<CandidateDto> findAll(Pageable pageable){
//        Page<Candidate> allCandidates = service.findAll(Status.ACTIVE, pageable);
//
//        return allCandidates.map(CandidateMapper::candidateDto);
//    }

//    public CandidateDto add(CandidateAddParam param) {
//        Candidate candidate = new Candidate(param.getName(), param.getSurname(), param.getEmail(), new ArrayList<>(), new UserEntity());
//        candidate.setStatus(Status.ACTIVE);
//
//        return CandidateMapper.candidateDto(service.add(candidate));
//    }

    public CandidateDto update(Long id, CandidateUpdateParam param) {
        TestResult testResult = testResultService.findById(param.getTestResult(), Status.ACTIVE);
        Candidate updateCandidate = service.findById(id,Status.ACTIVE);
        updateCandidate.getTestResults().add(testResult);

        return CandidateMapper.candidateDto(service.add(updateCandidate));
    }

//    public void deleteById(Long id) {
//        Candidate candidate = service.findById(id, Status.ACTIVE);
//        candidate.setStatus(Status.DEACTIVATED);
//
//        service.add(candidate);
//    }
}
