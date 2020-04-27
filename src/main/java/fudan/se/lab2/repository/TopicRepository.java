package fudan.se.lab2.repository;

import fudan.se.lab2.domain.conference.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author PXY
 * topic 仓库
 */

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findTopicByTopicId(Long topicId);

}
