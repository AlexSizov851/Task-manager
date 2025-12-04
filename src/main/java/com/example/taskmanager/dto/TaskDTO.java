package com.example.taskmanager.dto;

import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskDTO {

    // Уникальный идентификатор задачи (Соответствует полю id в сущности Task)
    private Long id;

    // Заголовок задачи, короткое описание
    private String title;

    // Подробное описание задачи
    private String description;

    // Текущий статус задачи, использует enum TaskStatus (PLANNED, IN_PROGRESS, COMPLETED, CANCELLED)
    private TaskStatus status;

    // Приоритет задачи, использует enum TaskPriority (LOW, MEDIUM, HIGH, CRITICAL)
    private TaskPriority priority;

    // Срок выполнения задачи, может быть null
    private LocalDateTime dueDate;

    // Дата и время создания задачи, Автоматически устанавливается при создании
    private LocalDateTime createdDate;

    // ID пользователя задачи, внешний ключ к таблице users
    private Long userId;

    // Имя пользователя задачи
    private String userName;

    // ID группы задач, может быть null
    private Long taskGroupId;

    // Название группы задач
    private String taskGroupName;
}