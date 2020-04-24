package fudan.se.lab2.controller.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.service.adminPage.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyf
 * 这个类控制“管理员请求”
 * 具体响应内容的类是 AdminService
 */

@RestController
public class AdminPageController {

    private AdminService adminService;

    // 日志
    Logger logger = LoggerFactory.getLogger(AdminPageController.class);

    @Autowired
    public AdminPageController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/getConferenceApplications")
    public ResponseEntity<?> handleAdminGetConferenceRequest(@RequestBody AdminGetConferenceApplicationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request);
        return ResponseEntity.ok(adminService.getConferenceApplications(request));
    }

    @PostMapping("/admin/changeConferenceStatus")
    public ResponseEntity<?> handleAdminChangeConferenceStatusRequest(@RequestBody AdminChangeConferenceStatusRequest request) {
        logger.debug(request.toString());
        System.out.println(request);
        return ResponseEntity.ok(adminService.changeConferenceStatus(request));
    }
}


