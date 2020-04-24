package fudan.se.lab2.controller;

import fudan.se.lab2.controller.adminPage.AdminPageController;
import fudan.se.lab2.service.adminPage.AdminService;
import org.junit.jupiter.api.Test;

class AdminPageControllerTest {

    private AdminService service = new AdminService();
    private AdminPageController controller = new AdminPageController(service);

    @Test
    void handleAdminGetConferenceRequest() {
    }

    @Test
    void handleAdminChangeConferenceStatusRequest() {
    }
}