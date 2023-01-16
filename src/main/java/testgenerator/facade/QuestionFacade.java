package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.CandidateAnswer;
import testgenerator.model.domain.Question;
import testgenerator.model.domain.Seniority;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.CandidateAnswerDto;
import testgenerator.model.dto.QuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.QuestionMapper;
import testgenerator.model.params.QuestionParam;
import testgenerator.service.*;


@Service
@RequiredArgsConstructor
public class QuestionFacade {

    private final QuestionService service;
    private final TopicService topicService;
    private final SeniorityService seniorityService;

    public QuestionDto findById(Long id) {
        Question question = service.findById(id,Status.ACTIVE);

        return QuestionMapper.questionDto(question);
    }

    public Page<QuestionDto> findAll(Pageable pageable){
        Page<Question> allQuestions = service.findAll(Status.ACTIVE, pageable);

        return allQuestions.map(QuestionMapper::questionDto);
    }

    public QuestionDto add(QuestionParam param) {
        Topic topic = topicService.findById(param.getTopic(), Status.ACTIVE);
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);

        Question question = QuestionMapper.paramToQuestion(param, topic, seniority);

        return QuestionMapper.questionDto(service.add(question));
    }

    public QuestionDto update(Long id, QuestionParam param) {
        Topic topic = topicService.findById(param.getTopic(), Status.ACTIVE);
        Seniority seniority = seniorityService.findById(param.getSeniority(), Status.ACTIVE);

        Question updateQuestion = service.findById(id,Status.ACTIVE);

        Question question = QuestionMapper.updateQuestionWithParam(param, updateQuestion, topic, seniority);

        return QuestionMapper.questionDto(service.add(question));
    }

    public void deleteById(Long id) {
        Question question = service.findById(id, Status.ACTIVE);

        question.setStatus(Status.DEACTIVATED);

        service.add(question);
    }

}
