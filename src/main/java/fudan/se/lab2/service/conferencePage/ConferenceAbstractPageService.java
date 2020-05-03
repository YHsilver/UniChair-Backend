package fudan.se.lab2.service.conferencePage;

import fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request.UserGetPassedConferenceRequest;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceAbstractPageService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ConferenceAbstractPageService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                         JwtTokenUtil tokenUtil){
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
    }

    /**
     * user get passed conference to do operation
     *
     * @param request the UserGetPassedConferenceRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getPassedConference(UserGetPassedConferenceRequest request) {
        // Index and length information is ignored and simply all conference is returned now.
        // TODO: implement the paging query

        if(request.getIdentity().equals("Chair")){
            return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByChairmanAndStatus(
                    userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())), Conference.Status.PASS),
                    true);
        }else if(request.getIdentity().equals("Author")){
            return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByAuthorSetContainsAndStatus(
                    userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())), Conference.Status.PASS),
                    true);
        }else if(request.getIdentity().equals("Reviewer")){
            return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByReviewerSetContainsAndStatus(
                    userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())), Conference.Status.PASS),
                    true);
        }

        return UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByStatus(Conference.Status.PASS),
                true);
    }

}
