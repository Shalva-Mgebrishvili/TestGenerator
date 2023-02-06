package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.UserFacade;
import testgenerator.model.dto.UserDto;
import testgenerator.model.dto.UserShortDto;
import testgenerator.model.params.ChangeRoleParam;
import testgenerator.model.params.UserUpdateParam;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade facade;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserShortDto> currentUserProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.currentUserProfile());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR') or hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    @PostMapping("/{id}/change-role")
    public ResponseEntity<UserDto> changeRole (@PathVariable Long id, @RequestBody ChangeRoleParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.changeRole(id, param));
    }

}
