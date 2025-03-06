markdown
# API Documentation

## Base URL
`http://localhost:8080`

## 1. Пользователи

### 1.1 Обновление пароля
**POST** `/users/set_password`

Обновляет пароль пользователя.

#### Request Body:
```json
{
  "currentPassword": "string", 
  "newPassword": "string"
}
```

#### Responses:
- `200 OK` - Пароль успешно обновлён.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещен.

---

### 1.2 Получение информации об авторизованном пользователе
**GET** `/users/me`

Получает информацию о текущем авторизованном пользователе.

#### Responses:
- `200 OK` - Информация о пользователе.
- `401 Unauthorized` - Неавторизованный доступ.

---

### 1.3 Обновление информации об авторизованном пользователе
**PATCH** `/users/me`

Обновляет информацию о текущем пользователе.

#### Request Body:
```json
{
  "firstName": "string",
  "lastName": "string",
  "phone": "string"
}
```

#### Responses:
- `200 OK` - Информация обновлена.
- `401 Unauthorized` - Неавторизованный доступ.

---

### 1.4 Обновление аватара авторизованного пользователя
**PATCH** `/users/me/image`

Обновляет аватар пользователя.

#### Request Body (multipart/form-data):
```json
{
  "image": "binary file"
}
```

#### Responses:
- `200 OK` - Аватар обновлён.
- `401 Unauthorized` - Неавторизованный доступ.

---

## 2. Регистрация и Авторизация

### 2.1 Регистрация пользователя
**POST** `/register`

Регистрирует нового пользователя.

#### Request Body:
```json
{
  "username": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "phone": "+7...",
  "role": "USER"
}
```

#### Responses:
- `201 Created` - Пользователь успешно зарегистрирован.
- `400 Bad Request` - Ошибка в запросе.

---

### 2.2 Авторизация пользователя
**POST** `/login`

Авторизует пользователя.

#### Request Body:
```json
{
  "username": "string",
  "password": "string"
}
```

#### Responses:
- `200 OK` - Авторизация успешна.
- `401 Unauthorized` - Неверный логин или пароль.

---

## 3. Объявления

### 3.1 Получение всех объявлений
**GET** `/ads`

Получает список всех объявлений.

#### Responses:
- `200 OK` - Список объявлений.

---

### 3.2 Добавление объявления
**POST** `/ads`

Добавляет новое объявление.

#### Request Body (multipart/form-data):
```json
{
  "properties": {
    "title": "string",
    "price": "integer",
    "description": "string"
  },
  "image": "binary file"
}
```

#### Responses:
- `201 Created` - Объявление добавлено.
- `401 Unauthorized` - Неавторизованный доступ.

---

### 3.3 Получение комментариев объявления
**GET** `/ads/{id}/comments`

Получает комментарии для объявления.

#### Parameters:
- `id` - ID объявления.

#### Responses:
- `200 OK` - Список комментариев.
- `401 Unauthorized` - Неавторизованный доступ.
- `404 Not Found` - Объявление не найдено.

---

### 3.4 Добавление комментария к объявлению
**POST** `/ads/{id}/comments`

Добавляет комментарий к объявлению.

#### Parameters:
- `id` - ID объявления.

#### Request Body:
```json
{
  "text": "string"
}
```

#### Responses:
- `200 OK` - Комментарий добавлен.
- `401 Unauthorized` - Неавторизованный доступ.
- `404 Not Found` - Объявление не найдено.

---

### 3.5 Получение информации об объявлении
**GET** `/ads/{id}`

Получает информацию о конкретном объявлении.

#### Parameters:
- `id` - ID объявления.

#### Responses:
- `200 OK` - Информация о объявлении.
- `401 Unauthorized` - Неавторизованный доступ.
- `404 Not Found` - Объявление не найдено.

---

### 3.6 Удаление объявления
**DELETE** `/ads/{id}`

Удаляет объявление.

#### Parameters:
- `id` - ID объявления.

#### Responses:
- `204 No Content` - Объявление удалено.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещён.
- `404 Not Found` - Объявление не найдено.

---

### 3.7 Обновление объявления
**PATCH** `/ads/{id}`

Обновляет информацию об объявлении.

#### Parameters:
- `id` - ID объявления.

#### Request Body:
```json
{
  "title": "string",
  "price": "integer",
  "description": "string"
}
```

#### Responses:
- `200 OK` - Объявление обновлено.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещён.
- `404 Not Found` - Объявление не найдено.

---

### 3.8 Получение объявлений авторизованного пользователя
**GET** `/ads/me`

Получает список объявлений текущего пользователя.

#### Responses:
- `200 OK` - Список объявлений.
- `401 Unauthorized` - Неавторизованный доступ.

---

### 3.9 Обновление картинки объявления
**PATCH** `/ads/{id}/image`

Обновляет картинку объявления.

#### Parameters:
- `id` - ID объявления.

#### Request Body (multipart/form-data):
```json
{
  "image": "binary file"
}
```

#### Responses:
- `200 OK` - Картинка обновлена.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещён.
- `404 Not Found` - Объявление не найдено.

---

## 4. Комментарии

### 4.1 Удаление комментария
**DELETE** `/ads/{adId}/comments/{commentId}`

Удаляет комментарий к объявлению.

#### Parameters:
- `adId` - ID объявления.
- `commentId` - ID комментария.

#### Responses:
- `200 OK` - Комментарий удалён.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещён.
- `404 Not Found` - Комментарий не найден.

---

### 4.2 Обновление комментария
**PATCH** `/ads/{adId}/comments/{commentId}`

Обновляет комментарий.

#### Parameters:
- `adId` - ID объявления.
- `commentId` - ID комментария.

#### Request Body:
```json
{
  "text": "string"
}
```

#### Responses:
- `200 OK` - Комментарий обновлён.
- `401 Unauthorized` - Неавторизованный доступ.
- `403 Forbidden` - Доступ запрещён.
- `404 Not Found` - Комментарий не найден.

---
```
