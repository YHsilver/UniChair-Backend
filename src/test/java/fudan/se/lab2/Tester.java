package fudan.se.lab2;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairSendInvitationRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairStartReviewingRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerCheckPaperReviewedRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSubmitPaperReviewedRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.GeneralService.GetConferenceTopicsService;
import fudan.se.lab2.service.GeneralService.GetUserDetailsService;
import fudan.se.lab2.service.adminPage.AdminService;
import fudan.se.lab2.service.applicationPage.ApplicationService;
import fudan.se.lab2.service.conferencePage.ConferenceAbstractPageService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.AuthorIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ChairIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.GenericConferenceService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ReviewerIdentityService;
import fudan.se.lab2.service.messagePage.MessageService;
import fudan.se.lab2.service.registerPage.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
@SpringBootTest
public class Tester {
    private static int paperIdOfTester = 0;
    public UserRepository userRepository;
    public ConferenceRepository conferenceRepository;
    public PaperRepository paperRepository;
    public InvitationRepository invitationRepository;
    public ReviewRepository reviewRepository;
    public PaperPostsRepository paperPostsRepository;
    public JwtTokenUtil tokenUtil;

    public AdminService adminService;
    public ApplicationService applicationService;
    // conference Details Page
    public AuthorIdentityService authorIdentityService;
    public ChairIdentityService chairIdentityService;
    public GenericConferenceService genericConferenceService;
    public ReviewerIdentityService reviewerIdentityService;
    // conference Abstract Page
    public ConferenceAbstractPageService conferenceAbstractPageService;
    // general Service
    public GetConferenceTopicsService getConferenceTopicsService;
    public GetUserDetailsService getUserDetailsService;

    public MessageService messageService;
    public RegisterService registerService;

    @Autowired
    public Tester(UserRepository userRepository, ConferenceRepository conferenceRepository,
                  PaperRepository paperRepository, InvitationRepository invitationRepository,
                  ReviewRepository reviewRepository, PaperPostsRepository paperPostsRepository,
                  JwtTokenUtil tokenUtil, AdminService adminService, ApplicationService applicationService,
                  AuthorIdentityService authorIdentityService, ChairIdentityService chairIdentityService,
                  GenericConferenceService genericConferenceService, ReviewerIdentityService reviewerIdentityService,
                  ConferenceAbstractPageService conferenceAbstractPageService, GetConferenceTopicsService getConferenceTopicsService,
                  GetUserDetailsService getUserDetailsService, MessageService messageService, RegisterService registerService) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.paperRepository = paperRepository;
        this.invitationRepository = invitationRepository;
        this.reviewRepository = reviewRepository;
        this.paperPostsRepository = paperPostsRepository;
        this.tokenUtil = tokenUtil;
        this.adminService = adminService;
        this.applicationService = applicationService;
        this.authorIdentityService = authorIdentityService;
        this.chairIdentityService = chairIdentityService;
        this.genericConferenceService = genericConferenceService;
        this.reviewerIdentityService = reviewerIdentityService;
        this.conferenceAbstractPageService = conferenceAbstractPageService;
        this.getConferenceTopicsService = getConferenceTopicsService;
        this.getUserDetailsService = getUserDetailsService;
        this.messageService = messageService;
        this.registerService = registerService;
    }

    public User getNewUser(){
        User user = UserGenerator.getRandomUser();
        return registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getFullName(),
                user.getUnit(), user.getArea(), user.getEmail()));
    }

    public Conference getNewConference(User chair){
        Conference conference = ConferenceGenerator.getRandomConference(chair);
        applicationService.addConferenceApplication(new UserAddConferenceApplicationRequest(tokenUtil.generateToken(chair),
                conference.getConferenceAbbreviation(), conference.getConferenceFullName(), conference.getConferenceTime(),
                conference.getConferenceLocation(), conference.getContributeStartTime(), conference.getContributeEndTime(),
                conference.getResultReleaseTime(), conference.getIntroduction(), conference.getTopics()));
        adminService.changeConferenceStatus(new AdminChangeConferenceStatusRequest(
                conferenceRepository.findConferenceByConferenceFullName(conference.getConferenceFullName()).getConferenceId(),
                Conference.Status.PASS));
        return conferenceRepository.findConferenceByConferenceFullName(conference.getConferenceFullName());
    }

    public Paper submitNewPaper(Conference conference, User author){
        assert(conference.getStage().equals(Conference.Stage.CONTRIBUTION));

        User temAuthor1 = UserGenerator.getRandomUser();
        User temAuthor2 = UserGenerator.getRandomUser();
        String[] authors = new String[8];
        authors[0] = temAuthor1.getUsername();
        authors[1] = temAuthor1.getFullName();
        authors[2] = temAuthor1.getEmail();
        authors[3] = temAuthor1.getUnit();
        authors[4] = temAuthor2.getUsername();
        authors[5] = temAuthor2.getFullName();
        authors[6] = temAuthor2.getEmail();
        authors[7] = temAuthor2.getUnit();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.pdf",    //filename
                StringGenerator.getRandomString().getBytes()); //content

        genericConferenceService.submitPaper(new UserSubmitPaperRequest(tokenUtil.generateToken(author), conference.getConferenceId(),
                conference.getTopics(), "Title" + paperIdOfTester + ": " + StringGenerator.getRandomString(), authors,
                "Summary: " + StringGenerator.getRandomString(), mockMultipartFile));

        Iterator<Paper> iterator =  paperRepository.findPapersByAuthorAndConference(author, conference).iterator();
        Paper newPaper;
        while(iterator.hasNext()){
            newPaper = iterator.next();
            if(newPaper.getTitle().startsWith("Title" + paperIdOfTester)){
                return newPaper;
            }
        }
        return null;
    }

    public User[] addReviewers(Conference conference, int number){
        User[] pcs = new User[number];
        String[] reviewerUsername = new String[number];
        for(int i = 0; i < number; i++){
            pcs[i] = getNewUser();
            reviewerUsername[i] = pcs[i].getUsername();
        }

        User chair = conference.getChairman();
        // String token, Long conferenceId, String[] reviewerUsername, String message
        chairIdentityService.sendInvitation(new ChairSendInvitationRequest(tokenUtil.generateToken(chair),
                conference.getConferenceId(), reviewerUsername, "message"));

        //String token, Long invitationId, Invitation.Status status, String[] topics
        for(int i = 0; i < number; i++){
            Long invitationId = invitationRepository.findByReviewerAndConferenceAndStatus(pcs[i], conference, Invitation.Status.PENDING)
                    .iterator().next().getInvitationId();
            //Invitation invitation = invitationRepository.findByInvitationId(invitationId);
            //System.out.println(invitation);
            messageService.userDecideInvitations(new UserDecideInvitationsRequest(tokenUtil.generateToken(pcs[i]),
                    invitationId, Invitation.Status.PASS, conference.getTopics()));
        }
        return pcs;
    }

    public void reviewerReviewAllPapers(Conference conference, int grade){
        String confidence;
        if(grade <= 0){ grade = -2; confidence = "VER_LOW"; }
        else{ grade = 2; confidence = "VERY_HIGH"; }
        Set<Review> reviews = reviewRepository.findReviewsByConference(conference);
        for (Review review: reviews) {
            User reviewer = review.getReviewer();
            String reviewerToken = tokenUtil.generateToken(reviewer);
            Set<Paper> paperSet = review.getPapers();
            for (Paper paper: paperSet) {
                reviewerIdentityService.submitPaperReviewed(new ReviewerSubmitPaperReviewedRequest(reviewerToken,
                        paper.getPaperId(), grade, StringGenerator.getRandomString(), confidence));
            }
        }
    }

    public void reviewerCheckAllPaperReviewed(Conference conference){
        Set<Review> reviews = reviewRepository.findReviewsByConference(conference);
        for (Review review: reviews) {
            User reviewer = review.getReviewer();
            String reviewerToken = tokenUtil.generateToken(reviewer);
            Set<Paper> paperSet = review.getPapers();
            for (Paper paper: paperSet) {
                reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(reviewerToken, paper.getPaperId()));
            }
        }
    }

    public boolean startReviewing(Conference conference){
        User chair = conference.getChairman();
        String message = chairIdentityService.startReviewing(new ChairStartReviewingRequest(tokenUtil.generateToken(chair),
                conference.getConferenceId(), ChairStartReviewingRequest.Strategy.RANDOM));
        return message.equals("{\"message\":\" Reviewing start!\"}");
    }

    public void startContribution(Conference conference){
        assert(conference.getStage().equals(Conference.Stage.PREPARATION));
        chairIdentityService.changeConferenceStage(new ChairChangeConferenceStageRequest(tokenUtil.generateToken(conference.getChairman()),
                Conference.Stage.CONTRIBUTION, conference.getConferenceId()));
        assert(conference.getStage().equals(Conference.Stage.PREPARATION));
    }

    public void startReviewed(Conference conference){
        assert(conference.getStage().equals(Conference.Stage.REVIEWING));
        chairIdentityService.changeConferenceStage(new ChairChangeConferenceStageRequest(tokenUtil.generateToken(conference.getChairman()),
                Conference.Stage.REVIEWED, conference.getConferenceId()));
    }

}
