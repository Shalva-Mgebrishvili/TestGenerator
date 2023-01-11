package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Question;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.service.QuestionService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService service;

    public QuestionDto findById(Long id) throws Exception {
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

    public QuestionDto add(Question question) {
        return QuestionMapper.questionDto(question);
    }

    public QuestionDto update(Long id, Question question) throws Exception {
        Question updateQuestion = service.findById(id,Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));
        updateQuestion.setQuestionType(question.getQuestionType());
        updateQuestion.setPoint(question.getPoint());
        updateQuestion.setText(question.getText());
        updateQuestion.setTopic(question.getTopic());
        updateQuestion.setSeniority(question.getSeniority());
        service.add(updateQuestion);

        return QuestionMapper.questionDto(updateQuestion);
    }

    public void deleteById(Long id) throws Exception {
        Question question = service.findById(id, Status.ACTIVE).orElseThrow(() -> new Exception("Question with ID: " + id + " not found."));
        question.setStatus(Status.DEACTIVATED);
        service.add(question);
    }

}
