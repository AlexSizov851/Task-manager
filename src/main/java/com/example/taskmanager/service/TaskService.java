package com.example.taskmanager.service;

import com.example.taskmanager.entity.*;
import com.example.taskmanager.repository.TaskGroupRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskGroupRepository taskGroupRepository;

    // Создание новой задачи
    @Transactional
    public Task createTask(Task task) {

        // проверяем пользователя
        if (task.getUser() == null || task.getUser().getId() == null) {
            throw new IllegalArgumentException("Пользователь обязателен для создания задачи");
        }

        // Проверяем, существует ли пользователь
        Long userId = task.getUser().getId();
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalArgumentException("Пользователь с ID " + userId + " не найден");
        }

        // Устанавливаем статус по умолчанию, если не указан
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PLANNED);
        }

        // Устанавливаем приоритет по умолчанию, если не указан
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }

        return taskRepository.save(task);
    }

    // Поиск задачи по ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Задача по ID не найдена: " + id));
    }

    // Поиск всех задач пользователя
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    // Обновить изменить группу задачи
    @Transactional
    public Task changeTaskGroup(Long taskId, Long groupId) {
        Task task = getTaskById(taskId);

        if (groupId == null) {
            // Отвязываем от группы
            task.setTaskGroup(null);
        }

        return taskRepository.save(task);
    }

    // Обновить задачу
    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);

        // Обновляем поля, если они предоставлены
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }

        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }

        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }

        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }

        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }

        // Обновить группу, если указана
        if (taskDetails.getTaskGroup() != null && taskDetails.getTaskGroup().getId() != null) {
            Long groupId = taskDetails.getTaskGroup().getId();
            TaskGroup taskGroup = taskGroupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Группа задач не найдена: " + groupId));
            task.setTaskGroup(taskGroup);
        } else if (taskDetails.getTaskGroup() == null) {
            // Если передали null, отвязываем от группы
            task.setTaskGroup(null);
        }

        return taskRepository.save(task);
    }

    // Обновление статуса задачи
    @Transactional
        public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    // Удаление задачи
    @Transactional
        public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    //Получить статистику по задачам пользователя
         public Map<String, Long> getUserTaskStatistics(Long userId) {
        List<Object[]> statistics = taskRepository.getUserTaskStatistics(userId);

    // Преобразуем результат запроса в Map
        return statistics.stream()
                .collect(java.util.stream.Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]));
    }

    // Получить статистику по всем задачам в системе
    public Map<String, Long> getSystemTaskStatistics() {
        List<Object[]> statistics = taskRepository.getSystemTaskStatistics();

    // Преобразуем результат запроса в Map
        return statistics.stream()
                .collect(java.util.stream.Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]));
    }

    // Поиск всех задач в системе
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
