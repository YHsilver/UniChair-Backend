package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorModifyPaperRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Topic;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class AuthorIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;

    // 日志
    Logger logger = LoggerFactory.getLogger(AuthorIdentityService.class);

    @Autowired
    public AuthorIdentityService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                 PaperRepository paperRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
    }

    public List<JSONObject> getMyPapers(AuthorGetMyPapersRequest request) {
        User author = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Set<Paper> papers;
        if (request.getConferenceId().equals(-1L)) {
            papers = paperRepository.findPapersByAuthor(author);
        } else {
            Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
            if (conference == null || !conference.getAuthorSet().contains(author)) {
                return null;
            }
            papers = paperRepository.findPapersByAuthorAndConference(author, conference);
        }
        List<JSONObject> list = new ArrayList<>();
        for (Paper paper : papers
        ) {
            list.add(paper.toBriefJson());
        }
        return list;
    }

    public JSONObject getMyPaperDetails(AuthorGetMyPaperDetailsRequest request) {
        User author = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        if (author == null || paper == null || paper.getAuthor() != author) {
            return null;
        }
        return paper.toStandardJson();
    }

    public String modifyPaper(AuthorModifyPaperRequest request) {
        User author = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        if (conference == null || paper == null || !conference.getPaperSet().contains(paper)) {
            return "{\"message\":\"bad request!\"}";
        }
        if (conference.getChairman().getId().equals(author.getId())) {
            return "{\"message\":\"invalid submit or change from chair!\"}";
        }
        if (conference.getStage() != Conference.Stage.CONTRIBUTION || paper.getStatus() != Paper.Status.CONTRIBUTION) {
            return "{\"message\":\"Not Contribution Stage! Modification forbidden!\"}";
        }

        if (!UtilityService.checkStringLength(request.getTitle(), 1, 50)
                || !UtilityService.checkStringLength(request.getSummary(), 1, 800)
                || !UtilityService.isAuthorsValid(request.getAuthors())) {
            return "{\"message\":\"paper modify wrong, information format error!\"}";
        }

        // check the validation of all topics
        if (!UtilityService.isTopicsValidInConference(conference, request.getTopics())) {
            return "{\"message\":\"paper modify wrong, topics selected error!\"}";
        }

        MultipartFile multipartFile = request.getFile();
        if (multipartFile != null) {
            // get file name
            String fileName = multipartFile.getOriginalFilename();
            // get file suffix
            String suffix = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf('.'));
            if (!suffix.toLowerCase().equals("pdf")) {
                return "{\"message\":\"paper modify wrong, not pdf file!\"}";
            }
            // check over
            File excelFile;
            try {
                excelFile = File.createTempFile(String.valueOf(Math.random()), suffix);
                // MultipartFile to File
                multipartFile.transferTo(excelFile);
            } catch (IOException ex) {
                logger.trace("context", ex);   // Compliant
                return "{\"message\":\"paper modify wrong, unknown error occurs! Please try again later!\"}";
            }
            paper.setFile(excelFile);
        }

        paper.setTitle(request.getTitle());
        paper.setSummary(request.getSummary());
        paper.setPaperAuthors(request.getAuthors());
        // remove old topics
        Set<Topic> paperTopics = paper.getTopics();
        for (Topic oldTopic : paperTopics
        ) {
            oldTopic.getAuthors().remove(author);
            oldTopic.getPapers().remove(paper);
        }
        paperTopics.clear();
        // add new topics
        for (String topic : request.getTopics()) {
            Topic currTopic = conference.findTopic(topic);
            currTopic.getAuthors().add(author);
            currTopic.getPapers().add(paper);
            paperTopics.add(currTopic);
        }
        // modify success
        return "{\"message\":\"your paper submit success!\"}";
    }


}
