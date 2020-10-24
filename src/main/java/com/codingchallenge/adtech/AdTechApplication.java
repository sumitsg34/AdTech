package com.codingchallenge.adtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class AdTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdTechApplication.class, args);
    }

}
