# Лабораторная работа №1 по Информационной безопасности

Тема: Разработка защищенного REST API с
интеграцией в CI/CD

## Содержание
- [Стек](#стек)
- [Быстрый старт (docker-compose)](#как-запустить)
- [API](#api)
- [Подробно о мерах защиты](#меры-защиты)
- [CI/CD и отчёты безопасности](#cicd-и-отчёты-безопасности)
- [Скриншоты отчетов SAST/SCAСкриншоты отчетов SAST/SCA из раздела "Actions" / "CI/CD"](#cкриншоты отчетов SAST/SCA из раздела "Actions" / "CI/CD")
---

## Стек
- Java 21
- Spring Boot 3 (Web, Security, Data JPA)
- PostgreSQL
- JWT
- OWASP Java Encoder (экранирование HTML)
- Maven
- SAST: SpotBugs
- SCA: OWASP Dependency-Check
- GitHub Actions

---

## Как запустить (по умолчанию работает на <http://localhost:8080>)

### 1) `.env` в корне
```env
JWT_SIGNING_KEY=YOUR_SIGNING_KEY
```

```bash
docker compose up -d --build
docker compose logs -f app
```

---

## API

### Аутентификация
```
POST /auth/register - регистрация пользователя при вводе логина и пароля. В ответ приходит jwt токен
POST /auth/login - авторизация пользователя при вводе логина и пароля. В ответ приходит jwt токен
```

Пример ввода json для аутентификации:

```
{
  "username": "user",
  "password": "password"
}
```

### Методы для работы с людьми
```
GET    /api/data - получения списка всех людей, хранящихся на аккаунте пользователя
POST   /api/data - добавление нового человека в аккаунт пользователя. В параметрах json должно быть введено имя, фамилия и возраст добавляемого человека
```

Пример ввода json для ```POST   /api/data```:
```
{
  "name": "Maxim",
  "surname": "Maximov",
  "age": 20
}
```

---

## Меры защиты

### Аутентификация и управление сессией
- JWT (HS256); секрет в переменной `JWT_SIGNING_KEY`.
- Stateless: `SessionCreationPolicy.STATELESS`.
- Кастомный фильтр `JwtAuthenticationFilter` валидирует токен и заполняет `SecurityContext`.

### Контроль доступа
- `/auth/**` — публично.
- Остальные эндпоинты — требуется `Authorization: Bearer <token>`.

### Защита от XSS
- Ответы API — JSON, автоматически экранируются при помощи `JacksonHtmlEscapeConfig`
- Для демонстрации XSS есть HTML-рендер `/api/data/{id}/render`:
    - Экранирование пользовательского ввода через **Spring** (`HtmlUtils.htmlEscape`).

### Защита от SQLi
- Spring Data JPA (параметризованные запросы), без конкатенации SQL.
- Валидация DTO аннотациями `jakarta.validation`.

### Хранение паролей
- BCrypt (`BCryptPasswordEncoder`).
---

## CI/CD и отчёты безопасности

- GitHub Actions запускается на каждый push (кроме изменений в README.md).
- Поднимается сервис PostgreSQL.
- SAST: SpotBugs (`target/spotbugsXml.xml`).
- SCA: OWASP Dependency-Check (`target/dependency-check-report.html|json|xml`).

Артефакты с отчётами доступны в разделе Actions → Artifacts.

## Скриншоты отчетов SAST/SCA из раздела "Actions" / "CI/CD"

![sast.png](sast.png)
![sca.png](sca.png)
