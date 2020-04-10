package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.admin.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.request.admin.AdminGetConferenceRequest;
import fudan.se.lab2.service.AdminService;
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
public class AdminController {

    private AdminService adminService;

    // 日志
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/getConference")
    public ResponseEntity<?> handleAdminGetConferenceRequest(@RequestBody AdminGetConferenceRequest request) {
        logger.debug("AdminRequest: " + request.toString());
        System.out.println(request);
        // todo
        return ResponseEntity.ok(adminService.ShowConference(request));
    }

    @PostMapping("/admin/changeStatus")
    public ResponseEntity<?> handleAdminChangeConferenceStatusRequest(@RequestBody AdminChangeConferenceStatusRequest request) {
        logger.debug("AdminRequest: " + request.toString());
        System.out.println(request);
        return ResponseEntity.ok(adminService.changeConferenceStatus(request));
    }
}


