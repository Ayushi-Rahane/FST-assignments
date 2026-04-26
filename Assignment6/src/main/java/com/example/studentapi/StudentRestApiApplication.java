package com.example.studentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Assignment 6 - RESTful Web Service for Student Management
 * Main entry point for the Spring Boot application.
 * @SpringBootApplication enables auto-configuration, component scanning, etc.
 */
@SpringBootApplication
public class StudentRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentRestApiApplication.class, args);
    }
}
