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
import testgenerator.facade.TopicFacade;
import testgenerator.model.dto.TopicDto;
import testgenerator.model.params.TopicAddUpdateParam;

@RestController
@RequestMapping("/topics")

@RequiredArgsConstructor
public class TopicController {

    private final TopicFacade facade;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TopicDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<TopicDto> add(@RequestBody TopicAddUpdateParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.add(param));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @RequestBody TopicAddUpdateParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
