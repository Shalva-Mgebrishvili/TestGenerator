package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import testgenerator.entity.QuestionEntity;
import testgenerator.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping ("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/find/{id}")
    public QuestionEntity findById(@PathVariable long id) {
        return questionService.findById(id);
    }

    @GetMapping("/findAll")
    public List<QuestionEntity> findAll() {
        return questionService.findAll();
    }

    @PostMapping("/save")
    public String save(@RequestBody QuestionEntity questionEntity) {
        questionService.save(questionEntity);
        return "Question with ID " + questionEntity.getId() + " has been saved.";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable long id, @RequestBody QuestionEntity questionEntity) {
        questionService.update(id, questionEntity);
        return "Question with ID " + id + " has been updated.";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        questionService.deleteById(id);
        return "Question with ID " + id + " has been deleted.";
    }
}
