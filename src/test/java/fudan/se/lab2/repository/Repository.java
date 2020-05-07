package fudan.se.lab2.repository;

import fudan.se.lab2.domain.conference.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Repository {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private InvitationRepository invitationRepository;

    @Autowired
    public Repository(UserRepository userRepository, ConferenceRepository conferenceRepository,
                      PaperRepository paperRepository, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.paperRepository = paperRepository;
        this.invitationRepository = invitationRepository;
    }

}
