package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks") // Таблица tasks в БД
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Поле Id

    @Column(nullable = false)
    private String title; // Поле title

    @Column
    private String description; // Поле description

    // Аннотация для хранения enum как строки в БД
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status; // Поле status

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority; // Поле priority

    @Column(name = "due_date")
    private LocalDateTime dueDate; //Поле due_date

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; //Поле created_date

    // Связь "многие к одному" с User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user; //Поле user_id

    // Связь "многие к одному" с TaskGroup
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_group_id")
    @ToString.Exclude
    @JsonIgnore
    private TaskGroup taskGroup; //Поле taskGroup

    // метод для приема ID из JSON:
    @JsonProperty("userId")
    public void setUserId(Long userId) {
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            this.user = user;
        }
    }

    @JsonProperty("taskGroupId")
    public void setTaskGroupId(Long taskGroupId) {
        if (taskGroupId != null) {
            TaskGroup taskGroup = new TaskGroup();
            taskGroup.setId(taskGroupId);
            this.taskGroup = taskGroup;
        }
    }

    // геттеры для JSON:
    @JsonIgnore
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    @JsonIgnore
    public Long getTaskGroupId() {
        return taskGroup != null ? taskGroup.getId() : null;
    }
}




