package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.TopicDto;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TopicMapper;
import testgenerator.model.mapper.UserMapper;
import testgenerator.model.params.ChangeRoleParam;
import testgenerator.model.params.TopicAddUpdateParam;
import testgenerator.model.params.UserAddUpdateParam;
import testgenerator.service.CandidateService;
import testgenerator.service.KeycloakService;
import testgenerator.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFacade {

    private final UserService service;
    private final KeycloakService keycloakService;
    private final CandidateService candidateService;

    public UserDto findById(Long id) {
        UserEntity user = service.findById(id, Status.ACTIVE);

        return UserMapper.userDto(user);
    }

    public Page<UserDto> findAll(Pageable pageable){
        Page<UserEntity> allUsers = service.findAll(Status.ACTIVE, pageable);

        return allUsers.map(UserMapper::userDto);
    }

    public UserDto add(UserAddUpdateParam param) {
//        Stack stack = stackService.findById(param.getStack(), Status.ACTIVE);
//
//        Topic topic = TopicMapper.paramToTopic(param, stack);
//
//        return TopicMapper.topicDto(service.add(topic));
        return  null;
    }

    @Transactional
    public UserDto update(Long id, UserAddUpdateParam param) {
        UserEntity updateUser = service.findById(id,Status.ACTIVE);

        keycloakService.updateUserInKeycloak(updateUser, param);

        UserEntity user = UserMapper.updateUserWithParam(param, updateUser);

        return UserMapper.userDto(service.add(user));
    }

    @Transactional
    public void deleteById(Long id) {
        UserEntity user = service.findById(id, Status.ACTIVE);

        Candidate candidate = user.getCandidate();
        candidate.setStatus(Status.DEACTIVATED);
        candidateService.add(user.getCandidate());

        Response response = keycloakService.deleteUserInKeycloak(user.getEmail());

        if(response.getStatus() != HttpStatus.NO_CONTENT.value()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while trying to delete employee in authorization server!");
        }

        user.setStatus(Status.DEACTIVATED);
        service.add(user);

    }

    @Transactional
    public UserDto changeRole(Long id, ChangeRoleParam param) {
        UserEntity user = service.findById(id, Status.ACTIVE);

        keycloakService.changeUserKeycloakRole(user, param.getNewRoles().get(0));

        user.setRole(Role.valueOf(param.getNewRoles().get(0).toUpperCase()));

        return UserMapper.userDto(service.add(user));
    }
}
