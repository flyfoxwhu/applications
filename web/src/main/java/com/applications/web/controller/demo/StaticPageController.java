package com.applications.web.controller.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/static")
public class StaticPageController {

    private static final String STATIC_PAGE = "static";


    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @param page
     * @return
     */
    @RequestMapping(value = "/{page}")
    public String staticPage(@PathVariable("page") String page) {
        return "/views/static/" + page;
    }

}
