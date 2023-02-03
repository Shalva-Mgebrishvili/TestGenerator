package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestQuestionDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestQuestionMapper;
import testgenerator.service.*;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestQuestionFacade {

    private final TestQuestionService service;

    public TestQuestionDto findById(Long id) {
        TestQuestion testQuestion = service.findById(id, Status.ACTIVE);

        return TestQuestionMapper.testQuestionDto(testQuestion);
    }

    public Page<TestQuestionDto> findAll(Pageable pageable){
        Page<TestQuestion> allTestQuestions = service.findAll(Status.ACTIVE, pageable);

        return allTestQuestions.map(TestQuestionMapper::testQuestionDto);
    }

}
