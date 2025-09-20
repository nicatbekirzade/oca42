package com.example.oca42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class Oca42Application {

    public static void main(String[] args) {
        SpringApplication.run(Oca42Application.class, args);
    }

}
