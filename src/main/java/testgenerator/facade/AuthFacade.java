package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testgenerator.service.KeycloakService;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthFacade {

    private final KeycloakService keycloakService;
    private final String realm;

//    public TokenDto login(LoginParam param) {
//        return TokenMapper.tokenDto(keycloakService.login(param));
//    }

//    public void logout() {
//        keycloakService.logout(keycloakService.getSessionId());
//    }
}
