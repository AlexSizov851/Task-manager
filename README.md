# Проект Task Manager

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

# Управление пользователями
POST   /api/users          - Создать пользователя
GET    /api/users/{id}     - Поиск пользователя по ID
GET    /api/users          - Поиск всех пользователей
PUT    /api/users/{id}     - Обновить пользователя
DELETE /api/users/{id}     - Удалить пользователя

# Управление задачами
POST   /api/tasks                     - Создать задачу
GET    /api/tasks/{}                  - Поиск задачи по ID
PUT    /api/tasks/{id}                - Обновить задачу
PATCH  /api/tasks/{id}/status         - Обновить статус задачи
PATCH  /api/tasks/{id}/group          - Изменить группу задачи 
DELETE /api/tasks/{id}                - Удалить задачу
GET    /api/tasks/user/{userId}       - Поиск всех задач пользователя
GET    /api/tasks/user/{userId}/statistics         - Получить статистику по задачам пользователя
GET    /api/tasks/system/statistics                - Получить статистику по задачам в системе
GET    /api/tasks/all                  - Поиск всех задач

# Управление группами задач
POST   /api/task-groups               - Создать группу задач 
GET    /api/task-groups/{id}          - Поиск группы задач по ID
GET    /api/task-groups/user/{userId} - Поиск всех групп задач пользователя
PUT    /api/task-groups/{id}          - Обновить группу задач
DELETE /api/task-groups/{id}          - Удалить группу задач
GET    /api/task-groups/all           - Поиск всех групп задач в системе
GET    /api/task-groups/user/{userId}/statistics    - Получить статистику по группам задач пользователя

---
# Таблицы:
* users - Пользователи
* tasks - Задачи
* task_groups - Группы задач

