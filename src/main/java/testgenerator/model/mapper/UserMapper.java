package testgenerator.model.mapper;

import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.UserDto;
import testgenerator.model.dto.UserShortDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.params.SignUpParam;
import testgenerator.model.params.UserUpdateParam;

import java.util.ArrayList;

public class UserMapper {

    public static UserDto userDto(UserEntity user) {
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getRole());
    }

    public static UserEntity updateUserWithParam(UserUpdateParam param, UserEntity user) {
        user.setUsername(param.getUsername().toLowerCase());
        user.setName(param.getName());
        user.setSurname(param.getSurname());
        user.setEmail(param.getEmail().toLowerCase());

        return user;
    }

    public static UserEntity signUpParamToUser(SignUpParam param) {
        UserEntity user = new UserEntity(param.getUsername().toLowerCase(), param.getName(), param.getSurname(),
                param.getEmail().toLowerCase(), Role.USER, new ArrayList<>(), new ArrayList<>());
        user.setStatus(Status.ACTIVE);

        return user;
    }

    public static UserShortDto userShortDto(UserEntity user) {
        return new UserShortDto(user.getId(), user.getName(), user.getSurname(), user.getEmail());
    }
}
