package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Главная аннотация Spring Boot
@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        // Запуск Spring Boot приложения
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}