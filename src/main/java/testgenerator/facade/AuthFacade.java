package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.UserMapper;
import testgenerator.model.params.SignUpParam;
import testgenerator.service.KeycloakService;
import testgenerator.service.UserService;

import javax.ws.rs.core.Response;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final KeycloakService keycloakService;
    private final UserService userService;

    public void logout() {
        keycloakService.logout(keycloakService.getSessionId());
    }

    @Transactional
    public UserDto signUp(SignUpParam param) {
        if (userService.existsByEmail(param.getEmail(), Status.ACTIVE)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");
        }

        Response response = keycloakService.addUserInKeycloak(param, param.getPassword());

        if(response.getStatus() != HttpStatus.CREATED.value()){
          throw new ResponseStatusException(HttpStatus.CONFLICT, "User creation failed on keycloak");
        }

        keycloakService.changeUserKeycloakRole(UserMapper.signUpParamToUser(param), "USER");

        return UserMapper.userDto(userService.add(UserMapper.signUpParamToUser(param)));
    }

//    public void changePassword (PasswordChangeParam param) {
//        if(param.getNewPassword().equals(param.getOldPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords match");
//
//        String keycloakId = keycloakService.getPrincipalKeycloakId();
//
//        if(keycloakService.checkOldPassword(param, keycloakId));
//    }
}
