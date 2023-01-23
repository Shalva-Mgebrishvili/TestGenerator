package testgenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.enums.QuestionType;
import testgenerator.model.enums.Status;
import testgenerator.repository.QuestionRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository repository;

    public Question findById(Long id, Status status) {
        Optional<Question> question = repository.findByIdAndStatus(id, status);

        if(question.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with ID: " + id + " not found.");

        return question.get();
    }

    public Page<Question> findAll(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    public Question add(Question question) {
        return repository.save(question);
    }

    public void findQuestionsForTest(Status status, QuestionType questionType, List<Topic> topics, Seniority seniority, Integer numberOfQuestions, Set<Question> set){
        List<Question> questions = new ArrayList<>();

        for(Topic topic: topics) {
            questions.addAll(repository.findByQuestionTypeByTopicBySeniority(status, questionType, topic, seniority));
        }

        if(questions.size()<numberOfQuestions) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indicated number of " + questionType + "S are not available");

        Random random = new Random();
        Integer prevSize = set.size();

        while(set.size()!= prevSize + numberOfQuestions) {
            int randInt = random.nextInt(0,questions.size());

            set.add(questions.get(randInt));
        }
    }
}
