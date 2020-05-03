package fudan.se.lab2.service.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Topic;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.TopicRepository;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author LBW
 * 这个类是具体响应类
 * “响应服务”
 */

@Service
public class AdminService {

    private ConferenceRepository conferenceRepository;
    private TopicRepository topicRepository;

    // constructor
    @Autowired
    public AdminService(ConferenceRepository conferenceRepository, TopicRepository topicRepository) {
        this.conferenceRepository = conferenceRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * admin get conferences list of certain status (PASSED, PENDING, REJECTED)
     *
     * @param request the AdminGetConferenceApplicationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getConferenceApplications(AdminGetConferenceApplicationsRequest request) {
        return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByStatus(request.getStatus()), true);
    }

    /**
     * Admin change the status of a certain conference.
     * Retain the rights of changing a rejected application to a passed application, although not allowed in the frontend.
     * @param request the changeConferenceStatus request
     * @return return a message indicating conference's ID & Status
     */
    public String changeConferenceStatus(AdminChangeConferenceStatusRequest request) {
        Conference targetConference = this.conferenceRepository.findByConferenceId(request.getId());
        if(request.getStatus() != null && targetConference != null){
            User chair = targetConference.getChairman();
            targetConference.setStatus(request.getStatus());
            // if passed, add topic entities to repository and this conference instance
            if(request.getStatus() == Conference.Status.PASS){
                String[] topics = targetConference.getTopics();
                for(String topic: topics){
                    // chair is default reviewer for each topic
                    Topic tempTopic = new Topic(targetConference, topic);
                    tempTopic.getReviewers().add(chair);
                    targetConference.getTopicSet().add(tempTopic);
                }
                this.topicRepository.saveAll(targetConference.getTopicSet());
            }
            // chair is a default reviewer
            targetConference.getReviewerSet().add(chair);
            this.conferenceRepository.save(targetConference);
            return targetConference.getConferenceFullName() + "'s Status is " + targetConference.getStatus().toString() + " now!";
        }
        return "Invalid Request";
    }
}
