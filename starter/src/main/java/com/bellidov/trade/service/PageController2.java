package com.bellidov.trade.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController2.class);

    @RequestMapping("/home")
    public String mainPage(Model model) {
        LOGGER.info("Page request received");

        return "index";
    }
}
