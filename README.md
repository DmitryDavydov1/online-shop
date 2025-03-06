markdown
# API Documentation

## Описание

API для работы с пользователями, объявлениями и комментариями.

## Базовый URL

- `http://localhost:8080`

---

## Эндпоинты

### 1. **/users/set_password** (POST)

**Описание:** Обновление пароля пользователя.

#### Тело запроса:
```json
{
  "currentPassword": "string",
  "newPassword": "string"
}
```
- `currentPassword`: текущий пароль пользователя (минимум 8 символов, максимум 16).
- `newPassword`: новый пароль пользователя (минимум 8 символов, максимум 16).

#### Ответы:
- `200`: Пароль обновлен успешно.
- `401`: Неавторизованный запрос.
- `403`: Запрещено.

---

### 2. **/users/me** (GET)

**Описание:** Получение информации об авторизованном пользователе.

#### Ответ:
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "Иван",
  "lastName": "Иванов",
  "phone": "+7 123 456-78-90",
  "role": "USER",
  "image": "http://example.com/image.jpg"
}
```
- `id`: ID пользователя.
- `email`: Логин пользователя.
- `firstName`: Имя пользователя.
- `lastName`: Фамилия пользователя.
- `phone`: Телефон пользователя.
- `role`: Роль пользователя (`USER`, `ADMIN`).
- `image`: Ссылка на аватар пользователя.

#### Ответы:
- `200`: Информация о пользователе.
- `401`: Неавторизованный запрос.

---

### 3. **/users/me** (PATCH)

**Описание:** Обновление информации о текущем пользователе.

#### Тело запроса:
```json
{
  "firstName": "string",
  "lastName": "string",
  "phone": "+7 123 456-78-90"
}
```
- `firstName`: Имя пользователя (минимум 3 символа, максимум 10).
- `lastName`: Фамилия пользователя (минимум 3 символа, максимум 10).
- `phone`: Телефон пользователя (формат: `+7 123 456-78-90`).

#### Ответы:
- `200`: Информация о пользователе обновлена.
- `401`: Неавторизованный запрос.

---

### 4. **/users/me/image** (PATCH)

**Описание:** Обновление аватара пользователя.

#### Тело запроса:
- `image`: Файл изображения (обязательный параметр).

#### Ответы:
- `200`: Аватар успешно обновлен.
- `401`: Неавторизованный запрос.

---

### 5. **/register** (POST)

**Описание:** Регистрация нового пользователя.

#### Тело запроса:
```json
{
  "username": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "phone": "+7 123 456-78-90",
  "role": "USER"
}
```
- `username`: Логин пользователя (минимум 4 символа, максимум 32).
- `password`: Пароль пользователя (минимум 8 символов, максимум 16).
- `firstName`: Имя пользователя (минимум 2 символа, максимум 16).
- `lastName`: Фамилия пользователя (минимум 2 символа, максимум 16).
- `phone`: Телефон пользователя (формат: `+7 123 456-78-90`).
- `role`: Роль пользователя (`USER` или `ADMIN`).

#### Ответы:
- `201`: Пользователь успешно зарегистрирован.
- `400`: Некорректный запрос.

---

### 6. **/login** (POST)

**Описание:** Авторизация пользователя.

#### Тело запроса:
```json
{
  "username": "string",
  "password": "string"
}
```
- `username`: Логин пользователя (минимум 4 символа, максимум 32).
- `password`: Пароль пользователя (минимум 8 символов, максимум 16).

#### Ответы:
- `200`: Пользователь успешно авторизован.
- `401`: Неавторизованный запрос.

---

### 7. **/ads** (GET)

**Описание:** Получение всех объявлений.

#### Ответ:
```json
{
  "count": 10,
  "results": [
    {
      "author": 1,
      "image": "http://example.com/ad.jpg",
      "pk": 123,
      "price": 5000,
      "title": "Объявление 1"
    }
  ]
}
```
- `count`: Общее количество объявлений.
- `results`: Список объявлений (каждое объявление содержит: `author`, `image`, `pk`, `price`, `title`).

#### Ответы:
- `200`: Список объявлений успешно получен.

---

### 8. **/ads** (POST)

**Описание:** Добавление нового объявления.

#### Тело запроса:
```json
{
  "title": "string",
  "price": 5000,
  "description": "string",
  "image": "file"
}
```
- `title`: Заголовок объявления (минимум 4 символа, максимум 32).
- `price`: Цена объявления (целое число, минимум 0).
- `description`: Описание объявления (минимум 8 символов, максимум 64).
- `image`: Изображение для объявления (обязательное поле).

#### Ответы:
- `201`: Объявление успешно добавлено.
- `401`: Неавторизованный запрос.

---

### 9. **/ads/{id}/comments** (GET)

**Описание:** Получение всех комментариев к объявлению.

#### Параметры:
- `id`: ID объявления.

#### Ответ:
```json
{
  "count": 3,
  "results": [
    {
      "author": 1,
      "authorImage": "http://example.com/avatar.jpg",
      "authorFirstName": "Иван",
      "createdAt": 1616687400000,
      "pk": 1,
      "text": "Комментарий к объявлению."
    }
  ]
}
```
- `count`: Количество комментариев.
- `results`: Список комментариев (каждый комментарий содержит: `author`, `authorImage`, `authorFirstName`, `createdAt`, `pk`, `text`).

#### Ответы:
- `200`: Комментарии успешно получены.
- `401`: Неавторизованный запрос.
- `404`: Объявление не найдено.

---

### 10. **/ads/{id}/comments** (POST)

**Описание:** Добавление комментария к объявлению.

#### Параметры:
- `id`: ID объявления.

#### Тело запроса:
```json
{
  "text": "string"
}
```
- `text`: Текст комментария (минимум 8 символов, максимум 64).

#### Ответы:
- `200`: Комментарий успешно добавлен.
- `401`: Неавторизованный запрос.
- `404`: Объявление не найдено.

---

### 11. **/ads/{id}** (GET)

**Описание:** Получение информации о конкретном объявлении.

#### Параметры:
- `id`: ID объявления.

#### Ответ:
```json
{
  "pk": 123,
  "authorFirstName": "Иван",
  "authorLastName": "Иванов",
  "description": "Описание объявления.",
  "email": "user@example.com",
  "image": "http://example.com/ad.jpg",
  "phone": "+7 123 456-78-90",
  "price": 5000,
  "title": "Объявление 1"
}
```
- `pk`: ID объявления.
- `authorFirstName`: Имя автора объявления.
- `authorLastName`: Фамилия автора объявления.
- `description`: Описание объявления.
- `email`: Логин автора объявления.
- `image`: Ссылка на изображение объявления.
- `phone`: Телефон автора объявления.
- `price`: Цена объявления.
- `title`: Заголовок объявления.

#### Ответы:
- `200`: Информация об объявлении.
- `401`: Неавторизованный запрос.
- `404`: Объявление не найдено.

---

### 12. **/ads/{id}** (DELETE)

**Описание:** Удаление объявления.

#### Параметры:
- `id`: ID объявления.

#### Ответы:
- `204`: Объявление удалено.
- `401`: Неавторизованный запрос.
- `403`: Запрещено.
- `404`: Объявление не найдено.

---

### 13. **/ads/{id}** (PATCH)

**Описание:** Обновление информации об объявлении.

#### Параметры:
- `id`: ID объявления.

#### Тело запроса:
```json
{
  "title": "string",
  "price": 5000,
  "description": "string"
}
```
- `title`: Заголовок объявления (минимум 4 символа, максимум 32).
- `price`: Цена объявления (целое число, минимум 0).
- `description`: Описание объявления (минимум 8 символов, максимум 64).

#### Ответы:
- `200`: Объявление обновлено.
- `401`: Неавторизованный запрос.
- `403`: Запрещено.
- `404`: Объявление не найдено.

---
