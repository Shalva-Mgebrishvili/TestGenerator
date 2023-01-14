package testgenerator.model.mapper;

import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Status;
import testgenerator.model.params.UserParam;

public class UserMapper {

    public static UserDto userDto(UserEntity user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getRole());
    }

    public static UserEntity paramToUser(UserParam param) {
        UserEntity user = new UserEntity(param.getName(), param.getSurname(), param.getEmail(), param.getRole());
        user.setStatus(Status.ACTIVE);

        return user;
    }

    public static UserEntity updateUserWithParam(UserParam param, UserEntity user) {
        user.setName(param.getName());
        user.setSurname(param.getSurname());
        user.setEmail(param.getEmail());
        user.setRole(param.getRole());

        return user;
    }
}
