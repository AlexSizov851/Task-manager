package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_groups") // Таблица task_groups в БД
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // поле id

    @Column(nullable = false)
    private String name;  // поле name

    @Column
    private String description; // поле description

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; // поле createdDate

    // Связь "многие к одному" с User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    // Связь "один ко многим" с Task
    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<Task> tasks = new ArrayList<>(); //

    @JsonProperty("userId")
    public void setUserId(Long userId) {
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            this.user = user;
        }
    }

    @JsonIgnore
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
