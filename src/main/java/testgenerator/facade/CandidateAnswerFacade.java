package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateAnswerMapper;
import testgenerator.service.*;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateAnswerFacade {

    private final CandidateAnswerService service;

    public CandidateAnswerDto findById(Long id) {
        CandidateAnswer candidateAnswer = service.findById(id, Status.ACTIVE);

        return CandidateAnswerMapper.candidateAnswerDto(candidateAnswer);
    }

    public Page<CandidateAnswerDto> findAll(Pageable pageable){
        Page<CandidateAnswer> allCandidateAnswers = service.findAll(Status.ACTIVE, pageable);

        return allCandidateAnswers.map(CandidateAnswerMapper::candidateAnswerDto);
    }

}
