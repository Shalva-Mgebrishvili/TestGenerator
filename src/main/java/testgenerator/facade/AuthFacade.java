package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.UserDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.UserMapper;
import testgenerator.model.params.SignUpParam;
import testgenerator.service.KeycloakService;
import testgenerator.service.StackService;
import testgenerator.service.UserService;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final KeycloakService keycloakService;
    private final UserService userService;
    private final StackService stackService;

    public void logout() {
        keycloakService.logout(keycloakService.getSessionId());
    }

    @Transactional
    public UserDto register(SignUpParam param) {
        if (userService.existsByEmail(param.getEmail(), Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");

        if (userService.existsByUsername(param.getUsername(), Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this username already exists.");

        List<Stack> stackList = param.getStackIdList().stream().map(stackId -> stackService.findById(stackId, Status.ACTIVE)).toList();

        UserEntity user = userService.add(UserMapper.signUpParamToUser(param, stackList));

        Response response = keycloakService.addUserInKeycloak(user, oneTimePassword());
        keycloakService.changeUserKeycloakRole(user, user.getRole().name());

        if(response.getStatus() != HttpStatus.CREATED.value())
          throw new ResponseStatusException(HttpStatus.CONFLICT, "User creation failed on keycloak");

        return UserMapper.userDto(user);
    }

    private String oneTimePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "INVALID_SPECIAL_CHARS";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

}
