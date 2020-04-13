package fudan.se.lab2.controller;

import fudan.se.lab2.service.AdminService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    private AdminService service = new AdminService();
    private AdminController controller = new AdminController(service);

    @Test
    void handleAdminGetConferenceRequest() {
    }

    @Test
    void handleAdminChangeConferenceStatusRequest() {
    }
}