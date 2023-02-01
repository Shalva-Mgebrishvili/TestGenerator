package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.StackDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.StackMapper;
import testgenerator.model.params.StackAddUpdateParam;
import testgenerator.service.StackService;
import testgenerator.service.TopicService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StackFacade {

    private final StackService service;
    private final TopicService topicService;

    public StackDto findById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE);

        return StackMapper.stackDto(stack);
    }

    public Page<StackDto> findAll(Pageable pageable){
        Page<Stack> allStacks = service.findAll(Status.ACTIVE, pageable);

        return allStacks.map(StackMapper::stackDto);
    }

    public StackDto add(StackAddUpdateParam param) {
        List<Topic> topics = param.getTopics().stream().map(t -> topicService.findById(t, Status.ACTIVE)).toList();
        Stack stack = new Stack(param.getName(), topics);
        stack.setStatus(Status.ACTIVE);

        return StackMapper.stackDto(service.add(stack));
    }
//id
    public StackDto update(Long id, StackAddUpdateParam param) {
        List<Topic> topics = param.getTopics().stream().map(t -> topicService.findById(t, Status.ACTIVE)).toList();
        Stack updateStack = service.findById(id,Status.ACTIVE);

        updateStack.setName(param.getName());
        updateStack.setTopics(topics);

        return StackMapper.stackDto(service.add(updateStack));
    }

    public void deleteById(Long id) {
        Stack stack = service.findById(id, Status.ACTIVE);
        stack.setStatus(Status.DEACTIVATED);

        service.add(stack);
    }
}
