package fudan.se.lab2.service.applicationPage;

import fudan.se.lab2.controller.GetConferenceRequest;
import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.GeneralService.GetConferenceService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private GetConferenceService getConferenceService;
    private JwtTokenUtil tokenUtil;

    // constructor
    @Autowired
    public ApplicationService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                              GetConferenceService getConferenceService, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.getConferenceService = getConferenceService;
        this.tokenUtil = tokenUtil;
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看自己申请的会议)
     *
     * @param request the UserGetConferenceApplicationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getConferenceApplications(UserGetConferenceApplicationsRequest request) {
        GetConferenceRequest getConferenceRequest = new GetConferenceRequest();
        // WARNING!! low efficiency
        getConferenceRequest.setChair(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())));
        getConferenceRequest.setStatus(request.getStatus());
        getConferenceRequest.setBrief(true);
        return getConferenceService.getConference(getConferenceRequest);
    }

    /**
     * check whether the ConferenceRequest request can be successful(用户申请会议)
     *
     * @param request the ConferenceRequest request
     * @return return a successful message if success
     */
    public String addConferenceApplication(UserAddConferenceApplicationRequest request) {
        User chairMan = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference newConference = new Conference(chairMan,
                request.getConferenceAbbreviation(), request.getConferenceFullName(), request.getConferenceLocation(),  request.getConferenceTime().plusDays(1L),
                request.getContributeStartTime().plusDays(1L), request.getContributeEndTime().plusDays(1L),
                request.getResultReleaseTime().plusDays(1L), request.getIntroduction());
        chairMan.addConference(newConference);
        conferenceRepository.save(newConference);
        //默认成功
        return "{\"message\":\"conference application submit success!\"}";
    }

}
