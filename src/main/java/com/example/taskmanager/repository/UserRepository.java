package com.example.taskmanager.repository;

import com.example.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Найти пользователя по имени
       Optional<User> findByUsername(String username);

    // Найти пользователя по email
       Optional<User> findByEmail(String email);

    // Проверить существование пользователя по имени
       boolean existsByUsername(String username);

    // Проверить существование пользователя по email
       boolean existsByEmail(String email);
}
