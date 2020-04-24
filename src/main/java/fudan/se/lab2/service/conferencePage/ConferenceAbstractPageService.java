package fudan.se.lab2.service.conferencePage;

import fudan.se.lab2.controller.GetConferenceRequest;
import fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request.UserGetPassedConferenceRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.GeneralService.GetConferenceService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceAbstractPageService {

    private UserRepository userRepository;
    private JwtTokenUtil tokenUtil;
    private GetConferenceService getConferenceService;

    @Autowired
    public ConferenceAbstractPageService(UserRepository userRepository, JwtTokenUtil tokenUtil,
                                         GetConferenceService getConferenceService){
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        this.getConferenceService = getConferenceService;
    }

    /**
     * user get passed conference to do operations
     *
     * @param request the UserGetPassedConferenceRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getPassedConference(UserGetPassedConferenceRequest request) {
        // Index and length information is ignored and simply all conference is returned now.
        // TODO: implement the paging query
        GetConferenceRequest getConferenceRequest = new GetConferenceRequest();
        getConferenceRequest.setStatus(Conference.Status.PASS);
        getConferenceRequest.setBrief(true);

        if(request.getIdentity().equals("Chair")){
            getConferenceRequest.setChair(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())));
        }else if(request.getIdentity().equals("Author")){
            getConferenceRequest.setAuthor(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())));
        }else if(request.getIdentity().equals("Reviewer")){
            getConferenceRequest.setReviewer(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())));
        }

        return getConferenceService.getConference(getConferenceRequest);
    }

}
