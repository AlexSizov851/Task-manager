package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Найти все задачи пользователя
       List<Task> findByUser_Id(Long userId);

    // Найти задачи в группе
       List<Task> findByTaskGroup_Id(Long groupId);

    // Запрос выводит количество задач пользователя по каждому статусу (группировка по статусу)
      @Query("SELECT t.status, COUNT(t) FROM Task t WHERE t.user.id = :userId GROUP BY t.status")
      List<Object[]> getUserTaskStatistics(@Param("userId") Long userId);

    // Запрос выводит количество задач по всей системе и каждому статусу.
      @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
      List<Object[]> getSystemTaskStatistics();
}
