package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.TestFacade;
import testgenerator.model.dto.TestDto;
import testgenerator.model.params.TestParam;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestFacade facade;

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TestDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false)Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<TestDto> add(@RequestBody TestParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.add(param));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestDto> update(@PathVariable Long id, @RequestBody TestParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
