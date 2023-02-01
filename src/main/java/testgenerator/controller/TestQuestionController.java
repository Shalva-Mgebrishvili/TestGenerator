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
import testgenerator.facade.TestQuestionFacade;
import testgenerator.model.dto.TestQuestionDto;
import testgenerator.model.params.TestQuestionAddParam;
import testgenerator.model.params.TestQuestionUpdateParam;

@RestController
@RequestMapping("/test-questions")
@RequiredArgsConstructor
public class TestQuestionController {

    private final TestQuestionFacade facade;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TestQuestionDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TestQuestionDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<TestQuestionDto> add(@RequestBody TestQuestionAddParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.add(param));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestQuestionDto> update(@PathVariable Long id, @RequestBody TestQuestionUpdateParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
    }
}
