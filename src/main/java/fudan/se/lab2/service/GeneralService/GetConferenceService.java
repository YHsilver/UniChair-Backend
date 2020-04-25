package fudan.se.lab2.service.GeneralService;

import fudan.se.lab2.controller.GetConferenceRequest;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetConferenceService {
    // 会议仓库
    private ConferenceRepository conferenceRepository;

    @Autowired
    public GetConferenceService(ConferenceRepository conferenceRepository){
        this.conferenceRepository = conferenceRepository;
    }

    public List<JSONObject> getConference(GetConferenceRequest getConferenceRequest){
        List<JSONObject> list = new ArrayList<>();
        List<Conference> conferenceList = new ArrayList<>();
        // 指定会议Id
        if(getConferenceRequest.getConferenceId() != -1){
            if(getConferenceRequest.isBrief()){
                list.add(conferenceRepository.findByConferenceId(getConferenceRequest.getConferenceId()).toBriefJson());
            }else{
                list.add(conferenceRepository.findByConferenceId(getConferenceRequest.getConferenceId()).toFullJson());
            }
            return list;
        }

        Iterable<Conference> conferenceIterable = conferenceRepository.findAll();
        conferenceIterable.forEach(eachConference -> {
            conferenceList.add(eachConference);
        });

        if(getConferenceRequest.getChair() != null){
            conferenceList.retainAll(conferenceRepository.findByChairMan(getConferenceRequest.getChair()));
        }

        if(getConferenceRequest.getReviewer() != null){
            conferenceList.retainAll(conferenceRepository.findConferencesByReviewerSetContains(getConferenceRequest.getReviewer()));
        }

        if(getConferenceRequest.getAuthor() != null){
            conferenceList.retainAll(conferenceRepository.findConferencesByAuthorSetContains(getConferenceRequest.getAuthor()));
        }

        // 指定会议简称
        if(!getConferenceRequest.getConferenceAbbreviation().equals("*")){
            conferenceList.retainAll(conferenceRepository.findByConferenceAbbreviation(getConferenceRequest.getConferenceAbbreviation()));
        }

        // 指定会议全称
        if(!getConferenceRequest.getConferenceFullName().equals("*")){
            conferenceList.retainAll(conferenceRepository.findByConferenceFullName(getConferenceRequest.getConferenceFullName()));
        }

        if(getConferenceRequest.isBrief()){
            for (Conference tmpConference: conferenceList
            ) {
                list.add(tmpConference.toBriefJson());
            }
        }else{
            for (Conference tmpConference: conferenceList
            ) {
                list.add(tmpConference.toFullJson());
            }
        }

        return list;
    }
}