package fudan.se.lab2.service.conferencePage;

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
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
        return thisConference.toFullJson();
    }

    /**
     * User submit paper（用户投稿）
     *
     * @param request the UserRequest request
     * @return return message
     */
    public String submitPaper(UserSubmitPaperRequest request) throws IOException {
        User author = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        MultipartFile multipartFile = request.getFile();
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf('.'));
        File excelFile = File.createTempFile(String.valueOf(Math.random()), prefix);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
        Paper newPaper = new Paper(author, request.getConferenceId(), request.getTitle(),
                request.getSummary(), excelFile);
        author.getPapers().add(newPaper);
        paperRepository.save(newPaper);
        userRepository.save(author);
        deleteFile(excelFile);
        Conference tempConference = conferenceRepository.findByConferenceId(request.getConferenceId());
        tempConference.addAuthor(author);
        conferenceRepository.save(tempConference);
        // 成功
        return "{\"message\":\"your paper submit success!\"}";
    }

    /**
     * 删除 File，配合 submitPaper
     *
     * @param files multiFile
     */
    private void deleteFile(File... files) throws NoSuchFileException, DirectoryNotEmptyException, IOException {
        for (File file : files)
            if (file.exists()) {
                Files.delete(Paths.get(file.getPath()));
            }
    }


    /**
     * ???
     *
     * @param request ???
     * @return ???
     */
    public String getIdentity(UserGetIdentityRequest request) {
        String resp = "[";
        User currUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference currConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());

        if (currUser.getId().equals(currConference.getChairMan().getId())) {
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
