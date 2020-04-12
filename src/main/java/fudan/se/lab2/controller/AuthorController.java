package fudan.se.lab2.controller;

import fudan.se.lab2.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyf
 * 这个类控制“author请求”
 * 具体响应内容的类是 AthorService
 */

@RestController
public class AuthorController {

    private AuthorService authorService;

    // 日志
    Logger logger = LoggerFactory.getLogger(AuthorController.class);

//    public AuthorController() {
//        super();
//    }

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

}



