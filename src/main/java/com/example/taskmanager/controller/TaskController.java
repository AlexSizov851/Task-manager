package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Управление задачами")
public class TaskController {

    private final TaskService taskService;

    // Создание новой задачи
    @PostMapping
    @Operation(summary = "Создать новую задачу")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Получение задачи по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по ID")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // Получение всех задач пользователя
    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить все задачи пользователя")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.getUserTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    // Получение задач пользователя по статусу
    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "Получить задачи пользователя по статусу")
    public ResponseEntity<List<Task>> getUserTasksByStatus(
            @PathVariable Long userId,
            @PathVariable TaskStatus status) {
        List<Task> tasks = taskService.getUserTasksByStatus(userId, status);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить задачу")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    // Обновление статуса задачи
    @PatchMapping("/{id}/status")
    @Operation(summary = "Обновить статус задачи")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        Task updatedTask = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(updatedTask);
    }

    // Изменить группу задачи
    @PatchMapping("/{id}/group")
    @Operation(summary = "Изменить группу задачи")
    public ResponseEntity<Task> changeTaskGroup(
            @PathVariable Long id,
            @RequestParam(required = false) Long groupId) {
        Task updatedTask = taskService.changeTaskGroup(id, groupId);
        return ResponseEntity.ok(updatedTask);
    }

    // Удаление задачи
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Получение всех задач пользователя
    @GetMapping("/user/{userId}/statistics")
    @Operation(summary = "Получить статистику по задачам пользователя")
    public ResponseEntity<Map<String, Long>> getUserTaskStatistics(@PathVariable Long userId) {
        Map<String, Long> statistics = taskService.getUserTaskStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    // Получение статистики по всем задачам в системе
    @GetMapping("/system/statistics")
    @Operation(summary = "Получить статистику по задачам в системе")
    public ResponseEntity<Map<String, Long>> getSystemTaskStatistics() {
        Map<String, Long> statistics = taskService.getSystemTaskStatistics();
        return ResponseEntity.ok(statistics);
    }

    // Получение просроченных задач пользователя
    @GetMapping("/user/{userId}/overdue")
    @Operation(summary = "Получить просроченные задачи пользователя")
    public ResponseEntity<List<Task>> getOverdueTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.getOverdueTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    // Получение всех задач в системе
    @GetMapping("/all")
    @Operation(summary = "Получить все задачи в системе")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
}
