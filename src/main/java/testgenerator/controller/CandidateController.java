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
import testgenerator.facade.CandidateFacade;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.params.CandidateAddParam;
import testgenerator.model.params.CandidateCreateForYourselfParam;


@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateFacade facade;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping
    public ResponseEntity<Page<CandidateDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @PostMapping("{id}/create")
    public ResponseEntity<Void> create(@PathVariable("id") Long testId, @RequestBody CandidateAddParam param) {
        facade.create(testId, param);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @PostMapping("{id}/create-for-yourself")
    public ResponseEntity<Void> createForYourself(@PathVariable("id") Long testId,
                                                  @AuthenticationPrincipal Jwt jwt) {
        facade.createForYourself(testId, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    @PutMapping("/{id}")
//    public ResponseEntity<CandidateDto> update(@PathVariable Long id, @RequestBody CandidateUpdateParam param) {
//        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
//    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        facade.deleteById(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

}
