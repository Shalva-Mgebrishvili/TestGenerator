package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testgenerator.facade.AuthFacade;
import testgenerator.model.params.SignUpParam;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade facade;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        facade.logout();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpParam param) {
        facade.signUp(param);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
