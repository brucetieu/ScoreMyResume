package com.bruce.jobmatchr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,  org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        })
public class JobmatchrApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobmatchrApplication.class, args);
    }

}
