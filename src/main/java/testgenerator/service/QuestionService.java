package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import testgenerator.entity.QuestionEntity;
import testgenerator.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionEntity save(QuestionEntity questionEntity) {
        return questionRepository.save(questionEntity);
    }

    public QuestionEntity findById(long id) {
        return questionRepository.findById(id).get();
    }

    public List<QuestionEntity> findAll() {
        return questionRepository.findAll();
    }

    public void update(long id, QuestionEntity questionEntity) {
        QuestionEntity updateQuestion = findById(id);
        updateQuestion.setStack(questionEntity.getStack());
        updateQuestion.setLevel(questionEntity.getLevel());
        updateQuestion.setQuestionText(questionEntity.getQuestionText());
        questionRepository.save(updateQuestion);
    }

    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }
}
