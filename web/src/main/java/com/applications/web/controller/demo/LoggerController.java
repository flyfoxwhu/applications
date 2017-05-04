package com.applications.web.controller.demo;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hukaisheng
 * @date 2017/4/17.
 */
@Controller
public class LoggerController {

    @RequestMapping("/")
    @ResponseBody
    public String logbackLevel() throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger("root");
        String levelStr = logger.getLevel().levelStr;
        return levelStr;
    }

}
