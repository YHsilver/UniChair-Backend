package fudan.se.lab2.service;

import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import static fudan.se.lab2.service.UserService.getInvitationJsonObjects;

/**
 * @author hyf
 * 这个类是响应 chair controller
 */

@Service
public class ChairService {

    // 用户仓库
    private UserRepository userRepository;

    // 会议仓库
    private ConferenceRepository conferenceRepository;

    // 邀请函仓库
    private InvitationRepository invitationRepository;

    // 加密密码
    private PasswordEncoder passwordEncoder;

    // token
    private JwtTokenUtil tokenUtil;

    public ChairService() {

    }

    // constructor
    @Autowired
    public ChairService(UserRepository userRepository, InvitationRepository invitationRepository,
                        ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }


//    /**
//     * chair invite PC members
//     *
//     * @param request the ChairRequest request
//     * @return return successful message
//     */
//    public List<JSONObject> getReviewers(ChairSearchReviewersRequest request) {
//        String fullName = request.getFullName();
//        Iterable<User> users = this.userRepository.findAll();
//        List<JSONObject> list = Lists.newArrayList();
//
//        users.forEach(eachUser -> {
//            if (eachUser.getFullName().contains(fullName)) {
//                list.add(eachUser.toStandardJson());
//            }
//        });
//
//        return list;
//    }







}

