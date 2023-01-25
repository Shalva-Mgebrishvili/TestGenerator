package testgenerator.model.mapper;

import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.params.SignUpParam;
import testgenerator.model.params.UserAddUpdateParam;

public class UserMapper {

    public static UserDto userDto(UserEntity user) {
        CandidateDto candidate = CandidateMapper.candidateDto(user.getCandidate());

        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getRole(), candidate);
    }

    public static UserEntity updateUserWithParam(UserAddUpdateParam param, UserEntity user) {
        user.setName(param.getName());
        user.setSurname(param.getSurname());
        user.setEmail(param.getEmail());

        return user;
    }

    public static UserEntity signUpParamToUser(SignUpParam param) {
        UserEntity user = new UserEntity(param.getName(), param.getSurname(), param.getEmail(), Role.USER, new Candidate());
        user.setStatus(Status.ACTIVE);

        return user;
    }
}
