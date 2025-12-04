package com.example.taskmanager.repository;

import com.example.taskmanager.entity.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

    // Найти все группы пользователя
       List<TaskGroup> findByUser_Id(Long userId);

    // Найти группу по имени и пользователю
       TaskGroup findByNameAndUser_Id(String name, Long userId);

    // Запрос выводит массив объектов с названием группы задач и количеством задач в каждой группе
       @Query("SELECT tg.name, COUNT(t) FROM TaskGroup tg LEFT JOIN tg.tasks t WHERE tg.user.id = :userId GROUP BY tg.id, tg.name")
       List<Object[]> getGroupStatistics(@Param("userId") Long userId);
}
