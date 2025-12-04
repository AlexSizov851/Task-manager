package com.example.taskmanager.controller;

import com.example.taskmanager.entity.TaskGroup;
import com.example.taskmanager.service.TaskGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task-groups")
@RequiredArgsConstructor
@Tag(name = "Task Group Controller", description = "Управление группами задач")
public class TaskGroupController {

    private final TaskGroupService taskGroupService;

    // Создание новой группы задач
    @PostMapping
    @Operation(summary = "Создать новую группу задач")
    public ResponseEntity<TaskGroup> createTaskGroup(@RequestBody TaskGroup taskGroup) {
        TaskGroup createdGroup = taskGroupService.createTaskGroup(taskGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    // Получение группы задач по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получить группу задач по ID")
    public ResponseEntity<TaskGroup> getTaskGroup(@PathVariable Long id) {
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(id);
        return ResponseEntity.ok(taskGroup);
    }

    // Получение всех групп задач пользователя
    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить все группы задач пользователя")
    public ResponseEntity<List<TaskGroup>> getUserTaskGroups(@PathVariable Long userId) {
        List<TaskGroup> taskGroups = taskGroupService.getUserTaskGroups(userId);
        return ResponseEntity.ok(taskGroups);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить группу задач")
    public ResponseEntity<TaskGroup> updateTaskGroup(
            @PathVariable Long id,
            @RequestBody TaskGroup groupDetails) {
        TaskGroup updatedGroup = taskGroupService.updateTaskGroup(id, groupDetails);
        return ResponseEntity.ok(updatedGroup);
    }

    // Удаление группы задач
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить группу задач")
    public ResponseEntity<Void> deleteTaskGroup(@PathVariable Long id) {
        taskGroupService.deleteTaskGroup(id);
        return ResponseEntity.noContent().build();
    }

    // Cтатистика по группам задач пользователя
    @GetMapping("/user/{userId}/statistics")
    @Operation(summary = "Получить статистику по группам задач пользователя")
    public ResponseEntity<Map<String, Long>> getGroupStatistics(@PathVariable Long userId) {
        Map<String, Long> statistics = taskGroupService.getGroupStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    // Все группы задач в системе
    @GetMapping("/all")
    @Operation(summary = "Получить все группы задач в системе")
    public ResponseEntity<List<TaskGroup>> getAllTaskGroups() {
        List<TaskGroup> taskGroups = taskGroupService.getAllTaskGroups();
        return ResponseEntity.ok(taskGroups);
    }
}