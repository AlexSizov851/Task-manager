package com.example.taskmanager.service;

import com.example.taskmanager.entity.TaskGroup;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.TaskGroupRepository;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;
    private final UserRepository userRepository;

    // Создание новой группы задач
    @Transactional
    public TaskGroup createTaskGroup(TaskGroup taskGroup) {

        // Проверяем наличие userId
        if (taskGroup.getUser() == null || taskGroup.getUser().getId() == null) {
            throw new IllegalArgumentException("ID пользователя обязательно");
        }
        Long userId = taskGroup.getUser().getId();

        // Находим реального пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + userId));
        taskGroup.setUser(user);

        // Проверка уникальности названия
        TaskGroup existingGroup = taskGroupRepository.findByNameAndUser_Id(
                taskGroup.getName(), userId);

        if (existingGroup != null) {
            throw new IllegalArgumentException("Группа задач с именем '" +
                    taskGroup.getName() + "' уже существует для этого пользователя");
        }
        return taskGroupRepository.save(taskGroup);
    }

    // Поиск группы задач по ID
    public TaskGroup getTaskGroupById(Long id) {
        return taskGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Группа задач не найдена по ID: " + id));
    }

    // Поиск всех групп задач пользователя
    public List<TaskGroup> getUserTaskGroups(Long userId) {
        return taskGroupRepository.findByUser_Id(userId);
    }

    // Обновить группу задач
    @Transactional
    public TaskGroup updateTaskGroup(Long id, TaskGroup groupDetails) {
        TaskGroup taskGroup = getTaskGroupById(id);

        // Обновляем поля, если они указаны
        if (groupDetails.getName() != null && !groupDetails.getName().equals(taskGroup.getName())) {
            // Проверяем уникальность нового названия
            TaskGroup existingGroup = taskGroupRepository.findByNameAndUser_Id(
                    groupDetails.getName(), taskGroup.getUser().getId());

            if (existingGroup != null && !existingGroup.getId().equals(id)) {
                throw new IllegalArgumentException("Группа задач с именем '" +
                        groupDetails.getName() + "' уже существует для этого пользователя");
            }

            taskGroup.setName(groupDetails.getName());
        }

        if (groupDetails.getDescription() != null) {
            taskGroup.setDescription(groupDetails.getDescription());
        }

        return taskGroupRepository.save(taskGroup);
    }

    // Удаление группы задач
    @Transactional
    public void deleteTaskGroup(Long id) {
        TaskGroup taskGroup = getTaskGroupById(id);

    // Перед удалением группы отвязываем все задачи от этой группы
        taskGroup.getTasks().forEach(task -> task.setTaskGroup(null));

        taskGroupRepository.delete(taskGroup);
    }

    //  Получение статистики по группам задач пользователя
   public Map<String, Long> getGroupStatistics(Long userId) {
        List<Object[]> statistics = taskGroupRepository.getGroupStatistics(userId);

   // Преобразуем результат запроса в Map
        return statistics.stream()
                .collect(java.util.stream.Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1]));
    }

    // Получение всех групп задач в системе
    public List<TaskGroup> getAllTaskGroups() {
        return taskGroupRepository.findAll();
    }
}