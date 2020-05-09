package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorModifyPaperRequest;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthorIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;

    // 日志
    private Logger logger = LoggerFactory.getLogger(AuthorIdentityService.class);

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
        if (author == null || request.getConferenceId() == null) {
            return null;
        }
        if (request.getConferenceId().equals(-1L)) {
            papers = paperRepository.findPapersByAuthor(author);
        } else {
            Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
            if (conference == null) {
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
        if (author == null || paper == null || !paper.getAuthor().getId().equals(author.getId())) {
            return null;
        }
        return paper.toStandardJson();
    }

    public String modifyPaper(AuthorModifyPaperRequest request) {
        User author = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        if (paper == null || author == null || !paper.getAuthor().getId().equals(author.getId())
                || paper.getConference().getStage() != Conference.Stage.CONTRIBUTION || paper.getStatus() != Paper.Status.CONTRIBUTION) {
            throw new PaperSubmitOrModifyFailException("Invalid Request!");
        }

        String[][] authors = UtilityService.isAuthorsValid(request.getAuthors());
        if (!UtilityService.checkStringLength(request.getTitle(), 1, 50)
                || !UtilityService.checkStringLength(request.getSummary(), 1, 800)
                || authors == null || !UtilityService.isTopicsValidInConference(paper.getConference(), request.getTopics())) {
            throw new PaperSubmitOrModifyFailException("paper modify wrong, information format error or topics selected error!");
        }

        MultipartFile multipartFile = request.getFile();
        if (multipartFile != null) {
            // get file name
            String fileName = multipartFile.getOriginalFilename();
            // get file suffix
            if (fileName == null || fileName.equals("")) fileName = multipartFile.getName();
            int index = fileName.lastIndexOf('.');
            if (index == -1) {
                throw new PaperSubmitOrModifyFailException("paper modify wrong, not pdf file!");
            }
            if (!fileName.substring(index).toLowerCase().equals(".pdf")) {
                throw new PaperSubmitOrModifyFailException("paper modify wrong, not pdf file!");
            }
            // check over
            File excelFile;
            try {
                excelFile = File.createTempFile("PA_", ".pdf");
                // MultipartFile to File
                multipartFile.transferTo(excelFile);
            } catch (IOException ex) {
                logger.trace("context", ex);   // Compliant
                throw new PaperSubmitOrModifyFailException("paper modify wrong, unknown error occurs! Please try again later!");
            }
            paper.setFile(excelFile);
        }

        paper.setTitle(request.getTitle());
        paper.setSummary(request.getSummary());
        paper.setPaperAuthors(authors);
        paper.setTopics(request.getTopics());
        paperRepository.save(paper);
        // modify success
        return "{\"message\":\"your paper submit success!\"}";
    }


}
