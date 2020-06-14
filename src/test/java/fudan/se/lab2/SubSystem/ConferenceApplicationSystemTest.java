package fudan.se.lab2.SubSystem;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.service.adminPage.AdminService;
import fudan.se.lab2.service.applicationPage.ApplicationService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ConferenceApplicationSystemTest {
    /*
     * 会议的申请与管理员的通过和拒绝是系统中功能封闭的子系统，同时也是其他子系统和功能的基础
     * */

    private Tester tester;

    @Autowired
    public ConferenceApplicationSystemTest(Tester tester){
        this.tester = tester;
    }

    @Test
    void systemTest(){
        // 得到与子系统相关的 Service 实例，包括 adminService, applicationService
        AdminService adminService = tester.adminService;
        ApplicationService applicationService = tester.applicationService;

        // 申请会议需要一个已注册的用户，由于 登录注册 子系统已经通过测试，这里通过 tester 封装好了注册新用户的模块
        User userA = tester.getNewUser();
        String userAToken = tester.tokenUtil.generateToken(userA);
        User userB = tester.getNewUser();
        String userBToken = tester.tokenUtil.generateToken(userB);
        // 使用随机会议生成器生成一个以 user 为 chair 的 conference 对象
        Conference conferenceA = ConferenceGenerator.getRandomConference(userA);
        Conference conferenceB = ConferenceGenerator.getRandomConference(userB);
        // 用该对象的属性填充请求，applicationService 处理该请求
        applicationService.addConferenceApplication(new UserAddConferenceApplicationRequest(userAToken,
                conferenceA.getConferenceAbbreviation(), conferenceA.getConferenceFullName(), conferenceA.getConferenceTime(),
                conferenceA.getConferenceLocation(), conferenceA.getContributeStartTime(), conferenceA.getContributeEndTime(),
                conferenceA.getResultReleaseTime(), conferenceA.getIntroduction(), conferenceA.getTopics()));
        conferenceA = tester.conferenceRepository.findConferenceByConferenceFullName(conferenceA.getConferenceFullName());
        applicationService.addConferenceApplication(new UserAddConferenceApplicationRequest(userBToken,
                conferenceB.getConferenceAbbreviation(), conferenceB.getConferenceFullName(), conferenceB.getConferenceTime(),
                conferenceB.getConferenceLocation(), conferenceB.getContributeStartTime(), conferenceB.getContributeEndTime(),
                conferenceB.getResultReleaseTime(), conferenceB.getIntroduction(), conferenceB.getTopics()));
        conferenceB = tester.conferenceRepository.findConferenceByConferenceFullName(conferenceB.getConferenceFullName());
        // 此时用户应该可以得到自己发出的会议申请
        List<JSONObject> expected = new ArrayList<>();
        expected.add(conferenceA.toBriefJson());
        assertEquals(expected, applicationService.getConferenceApplications(new UserGetConferenceApplicationsRequest(userAToken, Conference.Status.PENDING)));
        // 管理员也可以看到用户新发出的会议申请
        expected.add(conferenceB.toBriefJson());
        assertEquals(expected, adminService.getConferenceApplications(new AdminGetConferenceApplicationsRequest(Conference.Status.PENDING)));
        // 管理员通过一个申请，拒绝一个申请(pass A, reject B)
        adminService.changeConferenceStatus(new AdminChangeConferenceStatusRequest(conferenceA.getConferenceId(), Conference.Status.PASS));
        adminService.changeConferenceStatus(new AdminChangeConferenceStatusRequest(conferenceB.getConferenceId(), Conference.Status.REJECT));
        conferenceA = tester.conferenceRepository.findByConferenceId(conferenceA.getConferenceId());
        conferenceB = tester.conferenceRepository.findByConferenceId(conferenceB.getConferenceId());
        // 用户查看自己被通过（拒绝）的会议申请
        List<JSONObject> passExpected = new ArrayList<>();
        passExpected.add(conferenceA.toBriefJson());
        assertEquals(passExpected, applicationService.getConferenceApplications(new UserGetConferenceApplicationsRequest(userAToken, Conference.Status.PASS)));
        List<JSONObject> rejectExpected = new ArrayList<>();
        rejectExpected.add(conferenceB.toBriefJson());
        assertEquals(rejectExpected, applicationService.getConferenceApplications(new UserGetConferenceApplicationsRequest(userBToken, Conference.Status.REJECT)));

    }

}
