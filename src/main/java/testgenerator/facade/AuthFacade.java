package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.UserMapper;
import testgenerator.model.params.SignUpParam;
import testgenerator.service.KeycloakService;
import testgenerator.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final KeycloakService keycloakService;
    private final UserService userService;

    public void logout() {
        keycloakService.logout(keycloakService.getSessionId());
    }

    @Transactional
    public void signUp(SignUpParam param) {

        if (userService.existsByEmail(param.getEmail(), Status.ACTIVE)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");
        }

        UserEntity user = userService.add(UserMapper.signUpParamToUser(param));
        keycloakService.addUser(user, param.getPassword());
    }
}
