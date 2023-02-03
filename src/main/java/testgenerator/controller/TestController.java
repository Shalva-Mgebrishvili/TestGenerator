package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.TestFacade;
import testgenerator.model.dto.TestDto;
import testgenerator.model.dto.TestInfoDto;
import testgenerator.model.dto.TestStartDto;
import testgenerator.model.params.TestAddParam;
import testgenerator.model.params.TestCorrectionParam;
import testgenerator.model.params.TestSubmitParam;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestFacade facade;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<TestDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping
    public ResponseEntity<Page<TestDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false)Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @PostMapping
    public ResponseEntity<TestDto> add(@RequestBody TestAddParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.add(param));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/submit")
    public ResponseEntity<Void> submit(@RequestBody TestSubmitParam param, @AuthenticationPrincipal Jwt jwt) {
        facade.submit(param, jwt);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @PutMapping("/correction")
    public ResponseEntity<TestDto> correction(@RequestBody TestCorrectionParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.correction(param));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/{id}/test-info")
    public ResponseEntity<TestInfoDto> testInfoForCandidate(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.testInfoForCandidate(id));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/{id}/test-start")
    public ResponseEntity<TestStartDto> testStart(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.testStart(id));
    }
}
