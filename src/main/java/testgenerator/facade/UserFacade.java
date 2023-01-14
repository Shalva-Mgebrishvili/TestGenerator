package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.UserMapper;
import testgenerator.model.params.UserParam;
import testgenerator.service.UserService;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService service;

    public UserDto findById(Long id) {
        UserEntity user = service.findById(id, Status.ACTIVE);

        return UserMapper.userDto(user);
    }

    public Page<UserDto> findAll(Pageable pageable){
        Page<UserEntity> allUsers = service.findAll(Status.ACTIVE, pageable);

        return allUsers.map(UserMapper::userDto);
    }

    public UserDto add(UserParam param) {
        UserEntity user = UserMapper.paramToUser(param);

        return UserMapper.userDto(service.add(user));
    }

    public UserDto update(Long id, UserParam param) {
        UserEntity updateUser = service.findById(id,Status.ACTIVE);

        UserEntity user = UserMapper.updateUserWithParam(param, updateUser);

        return UserMapper.userDto(service.add(user));
    }

    public void deleteById(Long id) {
        UserEntity user = service.findById(id, Status.ACTIVE);

        user.setStatus(Status.DEACTIVATED);

        service.add(user);
    }
}
