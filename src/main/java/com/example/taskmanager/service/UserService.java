package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok: генерирует конструктор с final полями
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // Создание нового пользователя
    @Transactional
    public User createUser(User user) {
        // Проверка уникальности имени пользователя
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь уже существует: " + user.getUsername());
        }
        return userRepository.save(user);
    }

       // ПОиск пользователя по ID
        public User getUserById(Long id) {
            return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден по ID: " + id));
        }

       // Поиск пользователя по имени
        public User getUserByUsername(String username) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден по имени: " + username));
        }

       // Поиск всех пользователей
         public List<User> getAllUsers() {
            return userRepository.findAll();
         }

    // Обновление пользователя
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        // Обновляем поля, если они указаны
        if (userDetails.getUsername() != null) {
            user.setUsername(userDetails.getUsername());
        }

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }

        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        return userRepository.save(user);
    }

    // Удаление пользователя
         @Transactional
         public void deleteUser(Long id) {
            User user = getUserById(id);
            userRepository.delete(user);
         }
}