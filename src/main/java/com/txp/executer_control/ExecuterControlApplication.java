package com.txp.executer_control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExecuterControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecuterControlApplication.class, args);
    }

}
