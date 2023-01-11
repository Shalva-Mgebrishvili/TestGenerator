package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.service.QuestionService;

@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService service;

    public QuestionDto findById(long id) throws Exception {
        Question question = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));

        return QuestionMapper.questionDto(question);
    }

    public Page<QuestionDto> findAll() throws Exception {
        Page<Question> allQuestions = service.findAll(Status.ACTIVE);

        if(allQuestions.isEmpty()) {
            throw new Exception("No questions found.");
        }

        return allQuestions.map(q-> QuestionMapper.questionDto(q));
    }

}
