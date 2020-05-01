package fudan.se.lab2.service.applicationPage;

import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.exception.ConferencException.IllegalConferenceApplicationException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.TopicRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private JwtTokenUtil tokenUtil;

    // constructor
    @Autowired
    public ApplicationService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                              JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看自己申请的会议)
     *
     * @param request the UserGetConferenceApplicationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getConferenceApplications(UserGetConferenceApplicationsRequest request) {
        return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.
                findConferencesByChairManAndStatus(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())),
                        request.getStatus()), true);
    }

    /**
     * check whether the ConferenceRequest request can be successful(用户申请会议)
     *
     * @param request the ConferenceRequest request
     * @return return a successful message if success
     */
    public String addConferenceApplication(UserAddConferenceApplicationRequest request) {
        User chairMan = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        // check time order
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate contributeStartTime = request.getContributeStartTime();
        LocalDate contributeEndTime = request.getContributeEndTime();
        LocalDate resultReleaseTime = request.getResultReleaseTime();
        LocalDate conferenceTime = request.getConferenceTime();
        if(yesterday.isAfter(contributeStartTime) || yesterday.isEqual(contributeStartTime)
            || contributeStartTime.isAfter(contributeEndTime) || contributeStartTime.isEqual(contributeEndTime)
                || contributeEndTime.isAfter(resultReleaseTime) || contributeEndTime.isEqual(resultReleaseTime)
            || resultReleaseTime.isAfter(conferenceTime) || resultReleaseTime.equals(conferenceTime)){
            throw new IllegalConferenceApplicationException("Time is not well ordered");
        }

        if(UtilityService.checkStringLength(request.getConferenceAbbreviation(), 1, -1)
            && UtilityService.checkStringLength(request.getConferenceFullName(), 1, -1)
            && UtilityService.checkStringLength(request.getConferenceLocation(), 1, -1)
            && UtilityService.checkStringLength(request.getIntroduction(), 1, -1)
            && request.getTopics() != null && request.getTopics().length >= 1){
            // remove all empty strings
            Set<String> topics = new HashSet<>(Arrays.asList(request.getTopics()));
            for (String topic:topics
                 ) {
                if(topic == null || topic.equals("")) topics.remove(topic);
            }
            // valid topic num is zero
            if(topics.size() == 0){
                throw new IllegalConferenceApplicationException("Required information missing!");
            }
            Conference newConference = new Conference(chairMan,
                    request.getConferenceAbbreviation(), request.getConferenceFullName(), request.getConferenceLocation(),  conferenceTime.plusDays(1L),
                    contributeStartTime.plusDays(1L), contributeEndTime.plusDays(1L),
                    resultReleaseTime.plusDays(1L), request.getIntroduction(), topics.toArray(new String[0]));
            chairMan.addConference(newConference);
            conferenceRepository.save(newConference);
            return "{\"message\":\"conference application submit success!\"}";
        }

        // required information missing, throw exception
        throw new IllegalConferenceApplicationException("Required information missing!");
    }

}
