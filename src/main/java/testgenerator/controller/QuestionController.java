package testgenerator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import testgenerator.facade.QuestionFacade;
import testgenerator.model.dto.QuestionDto;

import java.util.List;

@RestController
@RequestMapping ("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionFacade questionFacade;

    @GetMapping("/find/{id}")
    public QuestionDto findById(@PathVariable long id) throws Exception {
        return questionFacade.findById(id);
    }

    @GetMapping("/findAll")
    public Page<QuestionDto> findAll() throws Exception {
        return questionFacade.findAll();
    }
//
////    @PostMapping("/save")
////    public String save(@RequestBody QuestionEntity questionEntity) {
////        questionService.save(questionEntity);
////        return "Question with ID " + questionEntity.getQuestionId() + " has been saved.";
////    }
//
//    @PutMapping("/update/{id}")
//    public String update(@PathVariable long id, @RequestBody QuestionEntity questionEntity) {
//        questionService.update(id, questionEntity);
//        return "Question with ID " + id + " has been updated.";
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable long id) {
//        questionService.deleteById(id);
//        return "Question with ID " + id + " has been deleted.";
//    }
}
