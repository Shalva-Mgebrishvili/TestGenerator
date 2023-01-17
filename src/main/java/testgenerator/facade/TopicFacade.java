package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testgenerator.model.domain.Stack;
import testgenerator.model.domain.Topic;
import testgenerator.model.dto.TopicDto;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TopicMapper;
import testgenerator.model.params.TopicAddUpdateParam;
import testgenerator.service.StackService;
import testgenerator.service.TopicService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicFacade {

    private final TopicService service;
    private final StackService stackService;

    public TopicDto findById(Long id) {
        Topic topic = service.findById(id, Status.ACTIVE);

        return TopicMapper.topicDto(topic);
    }

    public Page<TopicDto> findAll(Pageable pageable){
        Page<Topic> allTopics = service.findAll(Status.ACTIVE, pageable);

        return allTopics.map(TopicMapper::topicDto);
    }

    public TopicDto add(TopicAddUpdateParam param) {
        Stack stack = stackService.findById(param.getStack(), Status.ACTIVE);
        
        Topic topic = TopicMapper.paramToTopic(param, stack);

        return TopicMapper.topicDto(service.add(topic));
    }

    public TopicDto update(Long id, TopicAddUpdateParam param) {
        Stack stack = stackService.findById(param.getStack(), Status.ACTIVE);

        Topic updateTopic = service.findById(id,Status.ACTIVE);

        Topic topic = TopicMapper.updateTopicWithParam(param, updateTopic, stack);

        return TopicMapper.topicDto(service.add(topic));
    }

    public void deleteById(Long id) {
        Topic topic = service.findById(id, Status.ACTIVE);

        topic.setStatus(Status.DEACTIVATED);

        service.add(topic);
    }
}
