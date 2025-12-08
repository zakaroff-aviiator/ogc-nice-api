# OGC Nice API

Backend REST API built with Spring Boot to manage OGC Nice teams and their players.

## Tech stack

- Java 17  
- Spring Boot 4  
  - Spring Web  
  - Spring Data JPA  
  - Validation (Jakarta)  
- PostgreSQL (dev)  
- H2 (tests)  
- Maven  
- Docker + Docker Compose  
- GitHub Actions (CI)

---

## Architecture

Package structure:

```
com.souhail.ogc_nice_api
 ├── controller
 ├── service
 │     └── impl
 ├── repository
 ├── entity
 ├── dto
 │     ├── request
 │     └── response
 ├── mapper
 └── exception
```

Relations:

- Team 1 — N Player

---

# Running the project

You can run the application in **two ways**:

---

# 1️⃣ Local Development (Maven + Local Docker Postgres)

### Start PostgreSQL with Docker

```bash
docker-compose up -d postgres
```

### Run the application

```bash
mvn spring-boot:run
```

API available at:

```
http://localhost:8080
```

---

# 2️⃣ Full Dockerized Application (Recommended)

The entire stack (API + Postgres) can be started with **one command**:

```bash
docker-compose up --build
```

This will:

1. Build the Spring Boot jar  
2. Build the Docker image from the Dockerfile  
3. Start Postgres  
4. Start the API container  
5. Expose API on port **8080**  

### Test:

```bash
curl http://localhost:8080/api/teams
```

---

# Docker Setup Details

### Dockerfile (multi-stage build)

```
# ==== Build stage ====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

COPY src ./src
RUN mvn -q -e -B package -DskipTests

# ==== Run stage ====
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```
version: "3.9"

services:

  postgres:
    image: postgres:16
    container_name: ogc-nice-postgres
    restart: always
    environment:
      POSTGRES_DB: ogc_nice
      POSTGRES_USER: ogc_user
      POSTGRES_PASSWORD: ogc_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  api:
    build: .
    container_name: ogc-nice-api
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/ogc_nice
      SPRING_DATASOURCE_USERNAME: ogc_user
      SPRING_DATASOURCE_PASSWORD: ogc_password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_PROFILES_ACTIVE: dev
    ports:
      - "8080:8080"
    restart: always

volumes:
  postgres_data:
```

---

# Endpoints

Base path: `/api/teams`

---

### POST /api/teams

```json
{
  "name": "OGC Nice",
  "acronym": "OGCN",
  "budget": 50000000,
  "players": [
    { "name": "Dante", "position": "DEFENDER" },
    { "name": "Thuram", "position": "MIDFIELDER" }
  ]
}
```

---

### GET /api/teams  
Supports pagination + sorting.

```
GET /api/teams?page=0&size=10&sortBy=name&direction=asc
```

---

### GET /api/teams/{id}

Returns team with players or **404 Not Found**.

---

### DELETE /api/teams/{id}

Deletes a team, returns **204 No Content**.

---

# Error handling

Handled globally:

- 400 → Validation errors  
- 404 → Team not found  
- 500 → All other exceptions  

---

# Tests

Tests use **H2** and the **test** profile.

Run locally:

```bash
mvn test
```

Configuration:  
`src/test/resources/application-test.yml`

---

# Continuous Integration (GitHub Actions)

Workflow file:

```
.github/workflows/ci.yml
```

CI runs on every push and pull request:

- Setup Java 17  
- Cache Maven  
- Run `mvn verify` (build + tests)

---

# Useful Commands

**Start full stack (recommended):**

```bash
docker-compose up --build
```

**Start only Postgres:**

```bash
docker-compose up -d postgres
```

**Run app locally:**

```bash
mvn spring-boot:run
```

**Build project:**

```bash
mvn clean install
```

---

# Project Status

Backend operational with:

- CRUD operations  
- Pagination + sorting  
- Entity relations  
- Mapper layer  
- DTOs  
- Custom exceptions  
- Global handler  
- CI pipeline  
- Full Docker support  

