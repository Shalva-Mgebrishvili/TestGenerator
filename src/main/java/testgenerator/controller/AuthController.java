package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testgenerator.facade.AuthFacade;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

//    @PostMapping("/login")
//    public ResponseEntity<TokenDto> login(@RequestBody LoginParam param) {
//        return ResponseEntity.status(HttpStatus.OK).body(authFacade.login(param));
//    }

//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout() {
//        authFacade.logout();
//    }
}
