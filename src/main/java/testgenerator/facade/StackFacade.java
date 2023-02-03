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
import testgenerator.model.params.AddDeleteUsersToStackParam;
import testgenerator.model.params.StackAddUpdateParam;
import testgenerator.service.StackService;
import testgenerator.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class StackFacade {

    private final StackService service;
    private final UserService userService;

    public StackDto findById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE);

        return StackMapper.stackDto(stack);
    }

    public Page<StackDto> findAll(Pageable pageable){
        Page<Stack> allStacks = service.findAll(Status.ACTIVE, pageable);

        return allStacks.map(StackMapper::stackDto);
    }

    public StackDto add(StackAddUpdateParam param) {
        Stack stack = new Stack(param.getName(), new ArrayList<>());
        stack.setStatus(Status.ACTIVE);

        return StackMapper.stackDto(service.add(stack));
    }
//id
    public StackDto update(Long id, StackAddUpdateParam param) {
        Stack updateStack = service.findById(id,Status.ACTIVE);

        updateStack.setName(param.getName());

        return StackMapper.stackDto(service.add(updateStack));
    }

    public void deleteById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE);
        stack.setStatus(Status.DEACTIVATED);

        service.add(stack);
    }

    public void addUsersToStack (Long stackId, AddDeleteUsersToStackParam param) {
        Stack stack = service.findById(stackId, Status.ACTIVE);

        param.getUserIdList().stream().map(user -> userService.findById(user, Status.ACTIVE)).
                forEach(user -> {
                    if(user.getStacks().contains(stack))
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "User with Id " + user.getId() + " has already have the given stack");

                    user.getStacks().add(stack);
                    userService.add(user);
                });
    }

    public void deleteUsersFromStack (Long stackId, AddDeleteUsersToStackParam param) {
        Stack stack = service.findById(stackId, Status.ACTIVE);

        param.getUserIdList().stream().map(user -> userService.findById(user, Status.ACTIVE)).
                forEach(user -> {
                    if(!user.getStacks().contains(stack))
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "User with Id " + user.getId() + " does not have the given stack");

                    user.getStacks().remove(stack);
                    userService.add(user);
                });
    }
}
