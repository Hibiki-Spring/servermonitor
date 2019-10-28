package com.huiliyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class ServermonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServermonitorApplication.class, args);
    }

}
