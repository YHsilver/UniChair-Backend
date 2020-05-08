package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class GenericConferenceService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public GenericConferenceService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                    PaperRepository paperRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
    }

    /**
     * 获得详细信息
     *
     * @param request the UserGetConferenceDetailsRequest request
     * @return return conferences info
     */
    public JSONObject getConferenceDetails(UserGetConferenceDetailsRequest request) {
        Conference thisConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());
        if (thisConference != null) return thisConference.toFullJson();
        return null;
    }

    /**
     * User submit paper（用户投稿）
     *
     * @param request the UserRequest request
     * @return return message
     */
    public String submitPaper(UserSubmitPaperRequest request) throws IOException {
        User author = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if (conference == null || conference.getChairman().getId().equals(author.getId())) {
            return "{\"message\":\"chair cannot submit paper!\"}";
        }
        if (conference.getStage() != Conference.Stage.CONTRIBUTION) {
            return "{\"message\":\"Not Contribution Stage!\"}";
        }

        MultipartFile multipartFile = request.getFile();
        if (multipartFile == null) {
            return "{\"message\":\"pdf file missing!\"}";
        }
        // get file name
        String fileName = multipartFile.getName();
        // get file suffix
        System.out.println("fileName: " + fileName);
        int index = fileName.lastIndexOf('.');
        if(index == -1){
            return "{\"message\":\"paper submit wrong, not pdf file!\"}";
        }
        String suffix = fileName.substring(index);
        if (!suffix.toLowerCase().equals(".pdf")) {
            return "{\"message\":\"paper submit wrong, not pdf file!\"}";
        }

        if (!UtilityService.checkStringLength(request.getTitle(), 1, 50)
                || !UtilityService.checkStringLength(request.getSummary(), 1, 800)
                || !UtilityService.isAuthorsValid(request.getAuthors())) {
            return "{\"message\":\"paper modify wrong, information format error!\"}";
        }

        File excelFile = File.createTempFile("PA_", suffix);
        System.out.println("excelFile in Submit: " + excelFile);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);

        // check the validation of all topics
        if (!UtilityService.isTopicsValidInConference(conference, request.getTopics())) {
            return "{\"message\":\"paper submit wrong, topics selected error!\"}";
        }

        Paper newPaper = new Paper(conference, author, request.getTitle(),
                request.getAuthors(), request.getSummary(), excelFile, request.getTopics());
        paperRepository.save(newPaper);

        deleteFile(excelFile);
        conference.getAuthorSet().add(author);
        conferenceRepository.save(conference);

        return "{\"message\":\"your paper submit success!\"}";
    }

    /**
     * 删除 File，配合 submitPaper
     *
     * @param files multiFile
     */
    private void deleteFile(File... files) throws IOException {
        for (File file : files) {
            if (file.exists()) {
                Files.delete(Paths.get(file.getPath()));
            }
        }
    }


    /**
     * user get identity
     *
     * @param request userGetIdentityRequest
     * @return an int array indicating user's identity
     */
    public String getIdentity(UserGetIdentityRequest request) {
        // three bits indicates user's identity
        String resp = "[";
        User currUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference currConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());
        if (currUser.getId().equals(currConference.getChairman().getId())) {
            resp += "0,";
        } else {
            resp += "1,";
        }
        if (currConference.getReviewerSet().contains(currUser)) {
            resp += "0,";
        } else {
            resp += "1,";
        }
        if (currConference.getAuthorSet().contains(currUser)) {
            resp += "0";
        } else {
            resp += "1";
        }
        return resp + "]";
    }

}
