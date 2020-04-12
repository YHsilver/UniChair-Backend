package fudan.se.lab2.controller;

import fudan.se.lab2.service.ReviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyf
 * 这个类控制“用户请求”
 * 具体响应内容的类是 UserService
 */

@RestController
public class ReviewerController {

    private ReviewerService reviewerService;

    // 日志
    Logger logger = LoggerFactory.getLogger(ReviewerController.class);

    @Autowired
    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

}



