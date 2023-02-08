package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testgenerator.facade.AuthFacade;
import testgenerator.model.dto.UserDto;
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

    @PreAuthorize("hasRole('REVIEWER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpParam param) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.register(param));
    }
}
