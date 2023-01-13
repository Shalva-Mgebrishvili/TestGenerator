package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Stack;
import testgenerator.model.dto.StackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.StackMapper;
import testgenerator.model.params.StackParam;
import testgenerator.service.StackService;

@Service
@RequiredArgsConstructor
public class StackFacade {

    private final StackService service;

    public StackDto findById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack with ID: " + id + " not found."));

        return StackMapper.stackDto(stack);
    }

    public Page<StackDto> findAll(Pageable pageable){
        Page<Stack> allStacks = service.findAll(Status.ACTIVE, pageable);

        return allStacks.map(StackMapper::stackDto);
    }

    public StackDto add(StackParam param) {
        Stack stack = StackMapper.paramToStack(param);

        return StackMapper.stackDto(service.add(stack));
    }

    public StackDto update(Long id, StackParam param) {
        Stack updateStack = service.findById(id,Status.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack with ID: " + id + " not found."));

        Stack stack = StackMapper.updateStackWithParam(param, updateStack);

        return StackMapper.stackDto(service.add(stack));
    }

    public void deleteById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack with ID: " + id + " not found."));

        stack.setStatus(Status.DEACTIVATED);

        service.add(stack);
    }
}
