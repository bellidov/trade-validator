package com.bellidov.trade.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.bellidov.trade.*"})
public class SpringBootStarter /*extends SpringBootServletInitializer */{
    
    public static void main( String[] args )
    {
        SpringApplication.run(SpringBootStarter.class, args);
    }
}

