package fudan.se.lab2.service.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.ReviewRepository;
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
    private ReviewRepository reviewRepository;

    // constructor
    @Autowired
    public AdminService(ConferenceRepository conferenceRepository, ReviewRepository reviewRepository) {
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
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
            // chair is a default reviewer
            targetConference.getReviewerSet().add(chair);
            Review review = new Review(targetConference, chair, targetConference.getTopics());
            this.reviewRepository.save(review);
            this.conferenceRepository.save(targetConference);
            return targetConference.getConferenceFullName() + "'s Status is " + targetConference.getStatus().toString() + " now!";
        }
        return "Invalid Request";
    }
}
