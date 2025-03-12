-- liquibase formatted  sql

-- changeset dmitryDavidov:1

-- Создание таблицы user_entity
CREATE TABLE user_entity
(
    id         BIGSERIAL PRIMARY KEY,      -- Идентификатор пользователя, BIGSERIAL автоинкремент
    email      VARCHAR(255) NOT NULL,      -- Столбец для email, не может быть пустым
    first_name VARCHAR(255) NOT NULL,      -- Столбец для имени, обязательный
    last_name  VARCHAR(255) NOT NULL,      -- Столбец для фамилии, обязательный
    phone      VARCHAR(255) NOT NULL,      -- Столбец для номера телефона, обязательный
    role       VARCHAR(50)  NOT NULL,      -- Столбец для роли пользователя, обязательный
    image      VARCHAR(255),               -- Столбец для хранения изображения (необязательный)
    password   VARCHAR(255) NOT NULL,      -- Столбец для пароля, обязательный
    CONSTRAINT unique_email UNIQUE (email) -- Уникальность email для каждого пользователя
);

-- Создание таблицы ad_entity
CREATE TABLE ad_entity
(
    pk          BIGSERIAL PRIMARY KEY,                                                         -- Идентификатор объявления, BIGSERIAL автоинкремент
    text        VARCHAR(255) NOT NULL,                                                         -- Столбец для текста объявления, обязательный
    author_id   BIGINT,                                                                        -- Внешний ключ для пользователя (owner), ссылается на user_entity(id)
    price       BIGINT       NOT NULL,                                                         -- Столбец для цены объявления, обязательный
    description VARCHAR(255),                                                                  -- Столбец для описания объявления (необязательный)
    image       VARCHAR(255),                                                                  -- Столбец для изображения объявления (необязательный)
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES user_entity (id) ON DELETE CASCADE -- Связь с пользователем
);

-- Создание таблицы comment_entity
CREATE TABLE comment_entity
(
    pk         BIGSERIAL PRIMARY KEY,                                                       -- Идентификатор комментария, BIGSERIAL автоинкремент
    user_id    BIGINT,                                                                      -- Внешний ключ для пользователя, ссылается на user_entity(id)
    text       TEXT   NOT NULL,                                                             -- Столбец для текста комментария, обязательный
    created_at BIGINT NOT NULL,                                                             -- Столбец для времени создания комментария (в UNIX timestamp)
    ad_id      BIGINT,                                                                      -- Внешний ключ для объявления, ссылается на ad_entity(pk)
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_entity (id) ON DELETE CASCADE, -- Связь с пользователем
    CONSTRAINT fk_ad FOREIGN KEY (ad_id) REFERENCES ad_entity (pk) ON DELETE CASCADE        -- Связь с объявлением
);

