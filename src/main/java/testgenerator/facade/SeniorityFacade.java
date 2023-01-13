package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Seniority;
import testgenerator.model.dto.SeniorityDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.SeniorityMapper;
import testgenerator.model.params.SeniorityParam;
import testgenerator.service.SeniorityService;

@Service
@RequiredArgsConstructor
public class SeniorityFacade {

    private final SeniorityService service;

    public SeniorityDto findById(Long id){
        Seniority seniority = service.findById(id, Status.ACTIVE);

        return SeniorityMapper.seniorityDto(seniority);
    }

    public Page<SeniorityDto> findAll(Pageable pageable){
        Page<Seniority> allSeniorities = service.findAll(Status.ACTIVE, pageable);

        return allSeniorities.map(SeniorityMapper::seniorityDto);
    }

    public SeniorityDto add(SeniorityParam param) {

        Seniority seniority = SeniorityMapper.paramToSeniority(param);

        return SeniorityMapper.seniorityDto(service.add(seniority));
    }


    public SeniorityDto update(Long id, SeniorityParam param) {

        Seniority updateSeniority = service.findById(id,Status.ACTIVE);

        Seniority seniority = SeniorityMapper.updateSeniorityWithParam(param, updateSeniority);

        return SeniorityMapper.seniorityDto(service.add(seniority));
    }

    public void deleteById(Long id) {
        Seniority seniority = service.findById(id, Status.ACTIVE);
        seniority.setStatus(Status.DEACTIVATED);

        service.add(seniority);
    }
}
