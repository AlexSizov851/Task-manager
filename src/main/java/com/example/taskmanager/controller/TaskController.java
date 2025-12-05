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
@Tag(name = "ЗАДАЧИ", description = "Task Controller")
public class TaskController {

    private final TaskService taskService;

    // Создание новой задачи
    @PostMapping
    @Operation(summary = "Создать задачу")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Получение задачи по ID
    @GetMapping("/{id}")
    @Operation(summary = "Найти задачу по ID")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // Получение всех задач пользователя
    @GetMapping("/user/{userId}")
    @Operation(summary = "Найти все задачи пользователя")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.getUserTasks(userId);
        return ResponseEntity.ok(tasks);
    }
    // Обновление  задачи
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

    // Изменить группу задач
    @PatchMapping("/{id}/group")
    @Operation(summary = "Изменить группу у задачи")
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
    @Operation(summary = "Получить отчет по задачам пользователя")
    public ResponseEntity<Map<String, Long>> getUserTaskStatistics(@PathVariable Long userId) {
        Map<String, Long> statistics = taskService.getUserTaskStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    // Получение статистики по всем задачам
    @GetMapping("/system/statistics")
    @Operation(summary = "Получить отчет по всем задачам")
    public ResponseEntity<Map<String, Long>> getSystemTaskStatistics() {
        Map<String, Long> statistics = taskService.getSystemTaskStatistics();
        return ResponseEntity.ok(statistics);
    }


    // Получение всех задач
    @GetMapping("/all")
    @Operation(summary = "Найти все задачи")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
}
