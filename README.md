# Проект Task Manager

## Описание проекта
Backend-сервис для управления задачами с использованием Spring Boot и PostgreSQL.

## Запуск проекта

```bash

1. Запустить базу данных с помощью Docker Compose:
docker-compose up -d

2. Сборка проекта:
./gradlew build

3. Запуск приложения:
./gradlew bootRun

----------------
# Доступ к приложению
Порт: 8081
Swagger: http://localhost:8081/swagger-ui.html

Основные эндпоинты API:
# Управление пользователями
POST   /api/users          - Создать пользователя
GET    /api/users/{id}     - Получить пользователя по ID
GET    /api/users          - Получить всех пользователей
PUT    /api/users/{id}     - Обновить пользователя
DELETE /api/users/{id}     - Удалить пользователя

# Управление задачами
POST   /api/tasks                     - Создать задачу
GET    /api/tasks/{}                  - Получить задачу по ID
GET    /api/tasks/user/{userId}       - Получить задачи пользователя
PUT    /api/tasks/{id}                - Обновить задачу
PATCH  /api/tasks/{id}/status         - Обновить статус задачи
PATCH  /api/tasks/{id}/group          - Изменить группу задачи 
DELETE /api/tasks/{id}                - Удалить задачу
GET    /api/tasks/user/{userId}       - Получить все задачи пользователя
GET    /api/tasks/user/{userId}/status/{status}    - Получить задачи пользователя по статусу
GET    /api/tasks/user/{userId}/statistics         - Получить статистику по задачам пользователя
GET    /api/tasks/user/{userId}/overdue            - Получить просроченные задачи пользователя
GET    /api/tasks/system/statistics                - Получить статистику по задачам в системе
GET    /api/tasks/all                  - Получить все задачи

# Управление группами задач
POST   /api/task-groups               - Создать группу задач
GET    /api/task-groups/{id}          - Получить группу по ID
GET    /api/task-groups/user/{userId} - Получить группы пользователя
PUT    /api/task-groups/{id}          - Обновить группу
DELETE /api/task-groups/{id}          - Удалить группу
GET    /api/task-groups/all           - Все группы в системе
GET    /api/task-groups/user/{userId}/statistics    - Получить статистику по группам задач пользователя


---
# Таблицы:
* users - Пользователи
* tasks - Задачи
* task_groups - Группы задач

# Связи:
* Пользователь может иметь много задач
* Пользователь может иметь много групп задач
* Задача принадлежит одному пользователю
* Задача может принадлежать одной группе задач
* Группа задач принадлежит одному пользователю
* Группа задач может содержать много задач
