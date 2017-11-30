package com.bellidov.trade.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String mainPage() {
        LOGGER.info("Page request received");

        return "index";
    }
}
