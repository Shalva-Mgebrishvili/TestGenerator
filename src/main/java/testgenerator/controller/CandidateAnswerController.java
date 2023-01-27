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
import testgenerator.facade.CandidateAnswerFacade;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.params.CandidateAnswerAddParam;
import testgenerator.model.params.CandidateAnswerUpdateParam;

@RestController
@RequestMapping("/candidate-answers")
@RequiredArgsConstructor
public class CandidateAnswerController {
    private final CandidateAnswerFacade facade;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CandidateAnswerDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @GetMapping
    public ResponseEntity<Page<CandidateAnswerDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return ResponseEntity.status(HttpStatus.OK).body(facade.findAll(pageable));
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
//    @PostMapping
//    public ResponseEntity<CandidateAnswerDto> add(@RequestBody CandidateAnswerAddParam param) {
//        return ResponseEntity.status(HttpStatus.OK).body(facade.add(param));
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('CORRECTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CandidateAnswerDto> update(@PathVariable Long id, @RequestBody CandidateAnswerUpdateParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(id, param));
    }
}