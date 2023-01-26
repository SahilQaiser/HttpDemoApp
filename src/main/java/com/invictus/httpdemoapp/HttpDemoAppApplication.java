package com.invictus.httpdemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpDemoAppApplication {

    public static void main(String[] args) {
        System.out.println("Started Server");
        SpringApplication.run(HttpDemoAppApplication.class, args);
    }

}
