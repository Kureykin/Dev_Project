# URL Shortener
---
Проєкт "URL Shortener" - це вебсервіс, розроблений для перетворення довгих URL-адрес в короткі. 
Користувач вводить довгий URL, а наш сервіс надає йому унікальний скорочений URL, 
який при переході автоматично перенаправляє на вихідний довгий URL
---

# Технологічний стек

## Backend

* Spring Boot
* Spring Data
* JWT
* FlyWay

## Tests

* jUnit5
* Mockito

## Database

* PostgreSQL

## Documentation

* OpenAPI 3
* Swagger UI

## Build Tool

* Gradle

## Containerization

* Docker
* Docker Compose

---

# Локальний запуск

## Передумови

Переконайтеся, що встановлено:

* Java 25+
* Gradle
* PostgreSQL
* Docker (опціонально)

---

## Створення .env

Створіть файл `.env` на основі прикладу:

```bash
cp .env .env
```

Заповніть необхідні значення.

---

## Запуск бази даних

Якщо використовується локальний PostgreSQL:

```bash
createdb project_db
```

Або через Docker:

```bash
docker compose up -d postgres
```

---

## Запуск застосунку

Через Maven:

```bash
./gradlew bootRun
```

або

```bash
gradlew.bat bootRun
```

Після запуску сервіс буде доступний за адресою:

```text
http://localhost:8080
```

---

# Збірка проєкту

```bash
./gradlew build
```
або

```bash
./gradlew build -x test
```
Після збірки jar-файл буде знаходитися у:

```text
build/libs/
```

Запуск jar-файлу:

```bash
java -jar build/libs/project-name.jar
```

---

# Запуск тестів

## Unit та Integration тести

```bash
./gradlew test
```

---

## Повний цикл перевірок

```bash
./gradlew check
```

---

## Генерація звіту про покриття

(якщо використовується JaCoCo)

```bash
./gradlew test jacocoTestReport
```

Звіт:

```text
build/reports/jacoco/test/html/index.html
```

---

# Отримання JWT Token

Для отримання JWT необхідно виконати запит:

```http
POST /user/v1/login
Content-Type: application/json

{
  "username": "john",
  "password": "Wr4a2dds8"
}
```
### Важлыво

````
    password має складатись мiнiмум з 8-ми символiв
    та обов`язково маты хочаб одну велику, малену лiтери та число
````


Приклад відповіді:

```header
-H "Authorization Bearer eyJhbGciOiJIUzI1NiIs..."
```

---

# Передача JWT Token

Для захищених endpoint-ів необхідно передавати JWT у заголовку:

```http
Authorization: Bearer <access-token>
```

Приклад:

```bash
curl \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIs..." \
http://localhost:8080/slicer/v1/list
```

---

# API Endpoints

## Authentication

| Method | Endpoint            | Description      |
| ------ |---------------------| ---------------- |
| POST   | `/user/v1/login`    | Авторизація      |
| POST   | `/user/v1/register` | Реєстрація       |


---

## Slicer

| Method | Endpoint                       | Description                         |
|--------|--------------------------------|-------------------------------------|
| GET    | `/slicer/v1/list`              | Отримати список посилань            |
| GET    | `/slicer/v1/list/active`       | Отримати список активних посилань   |
| DELETE | `/slicer/v1/url/{id}`          | Видалити посиланя                   |
| PUT    | `/slicer/v1/url`               | Оновити посилань                    |
| GET    | `/slicer/v1/url/redirect/{id}` | Перенаправити по первыному посиланю |


# Swagger / OpenAPI

Swagger UI:

```text
src/main/resources/SwaggerDoc.yml
```

---

# Docker

## Збірка образу

```bash
docker build -t app.
```

---

## Запуск контейнера

```bash
docker run -d \
-p 8080:8080 \
--env-file .env \
--name app \
app
```

---

# Docker Compose

## Запуск усіх сервісів

```bash
docker compose up -d
```

---

## Перегляд логів

```bash
docker compose logs -f
```

---

## Зупинка сервісів

```bash
docker compose down
```

---

# Структура проєкту

```text
src
├── main
│   ├── java
│   │   └── com.example.app
│   │       ├── controller
│   │       ├── db
│   │       │   ├──entity
│   │       │   └──repository
│   │       ├── requests
│   │       ├── response
│   │       ├── security
│   │       │   ├──configurer
│   │       │   └──filters
│   │       ├── service
│   │       └── until
│   │           └── exception
│   │
│   └── resources
│       ├── application.properties
│       ├── SwaggerDoc.yml
│       └── db
│           └── migration
│
└── test
    └── java
        └── com.example.app
```

---