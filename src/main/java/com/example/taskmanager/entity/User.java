package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Аннотация для сущности JPA
@Entity

// Аннотация для таблицы в БД
@Table(name = "users")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // паттерн Builder

public class User {

    // Аннотация для первичного ключа
    @Id
    // Аннотация для автоинкремента
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Поле id

    @Column(nullable = false, unique = true)
    private String username; // Поле username

    @Column(nullable = false, unique = true)
    private String email; // Поле email

    @Column(nullable = false)
    private String password; // Поле password

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; // Поле createdDate

    // Связь "один ко многим" с Task
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude//Исключить из toString
    @JsonIgnore
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    // Связь "один ко многим" с TaskGroup
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<TaskGroup> taskGroups = new ArrayList<>();
}