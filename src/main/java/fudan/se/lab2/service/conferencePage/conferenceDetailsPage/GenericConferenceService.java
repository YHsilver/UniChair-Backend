package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetPaperPdfFileRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.exception.ConferencException.PaperSubmitOrModifyFailException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        if (author == null || conference == null || conference.getChairman().getId().equals(author.getId())) {
            throw new PaperSubmitOrModifyFailException("Invalid identity!");
        }
        if (conference.getStage() != Conference.Stage.CONTRIBUTION) {
            throw new PaperSubmitOrModifyFailException("Not Contribution Stage!");
        }

        MultipartFile multipartFile = request.getFile();
        if (multipartFile == null) {
            throw new PaperSubmitOrModifyFailException("pdf file missing!");
        }
        // get file name
        String fileName = multipartFile.getOriginalFilename();

        if(fileName == null){
            throw new PaperSubmitOrModifyFailException("paper submit wrong, not pdf file!");
        }
        if (fileName.equals(""))fileName=multipartFile.getName();

        int index = fileName.lastIndexOf('.');
        if(index == -1){
            throw new PaperSubmitOrModifyFailException("paper submit wrong, not pdf file!");
        }
        String suffix = fileName.substring(index);
        if (!suffix.toLowerCase().equals(".pdf")) {
            throw new PaperSubmitOrModifyFailException("paper submit wrong, not pdf file!");
        }

        String[][] authors = UtilityService.isAuthorsValid(request.getAuthors());
        if (!UtilityService.checkStringLength(request.getTitle(), 1, 50)
                || !UtilityService.checkStringLength(request.getSummary(), 1, 800)
                || authors == null) {
            throw new PaperSubmitOrModifyFailException("paper submit wrong, information format error!!");
        }

        File excelFile = File.createTempFile("PA_", suffix);
        System.out.println("excelFile in Submit: " + excelFile);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
        System.out.println(excelFile.length());
        // check the validation of all topics
        if (!UtilityService.isTopicsValidInConference(conference, request.getTopics())) {
            throw new PaperSubmitOrModifyFailException("paper submit wrong, topics selected error!");
        }

        Paper newPaper = new Paper(conference, author, request.getTitle(),
                authors, request.getSummary(), excelFile, request.getTopics());
        newPaper.setFileName(fileName);
        paperRepository.save(newPaper);

//        deleteFile(excelFile);
        conference.getAuthorSet().add(author);
        conferenceRepository.save(conference);

        return "{\"message\":\"your paper submit success!\"}";
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

    public ResponseEntity<byte[]> getPaperPdfFile(UserGetPaperPdfFileRequest request){
        // 读取pdf文件到字节里
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        byte[] contents;
        HttpHeaders headers = new HttpHeaders();
        if(paper == null){
            contents = "No Such Paper".getBytes();
            headers.setContentType(MediaType.parseMediaType("text/plain"));
            return new ResponseEntity<>(contents, headers, HttpStatus.BAD_REQUEST);
        }

        File tarFile = paper.getFile();

        try{
            contents = Files.readAllBytes(tarFile.toPath());
        }catch (IOException ioe){
            contents = "Get File Error.".getBytes();
            headers.setContentType(MediaType.parseMediaType("text/plain"));
            return new ResponseEntity<>(contents, headers, HttpStatus.BAD_REQUEST);
        }

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = tarFile.getName();
        headers.setContentDispositionFormData("attachment", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }


}
