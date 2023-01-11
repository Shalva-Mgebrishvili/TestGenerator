package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.QuestionFacade;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;

import java.util.List;

@RestController
@RequestMapping ("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionFacade questionFacade;

    @GetMapping("/find/{id}")
    public ResponseEntity<QuestionDto> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<QuestionDto>> findAll() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> add(@RequestBody Question question) {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.add(question));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDto> update(@PathVariable Long id, @RequestBody Question question) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(questionFacade.update(id, question));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        questionFacade.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
