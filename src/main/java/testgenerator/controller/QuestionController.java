package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.QuestionFacade;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.params.QuestionParam;


@RestController
@RequestMapping ("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionFacade questionFacade;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<QuestionDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<QuestionDto> add(@RequestBody QuestionParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.add(param));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> update(@PathVariable Long id, @RequestBody QuestionParam param) {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.update(id, param));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionFacade.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
