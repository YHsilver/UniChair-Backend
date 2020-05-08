package fudan.se.lab2.service.GeneralService;

import fudan.se.lab2.controller.GetConferenceTopicsRequest;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetConferenceTopicsService {
    private ConferenceRepository conferenceRepository;

    @Autowired
    public GetConferenceTopicsService(ConferenceRepository conferenceRepository){
        this.conferenceRepository = conferenceRepository;
    }

    public String[] getConferenceTopics(GetConferenceTopicsRequest request){
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(conference == null){
            return null;
        }
        return conference.getTopics();
    }



}
