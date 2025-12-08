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
- Docker (Postgres)
- GitHub Actions (CI)

---

## Architecture

Package structure:

- `controller` – REST endpoints (`/api/teams`)
- `service` – business logic (pagination, sorting, validation)
- `repository` – Spring Data JPA repositories
- `entity` – JPA entities (`Team`, `Player`)
- `dto` – request/response DTOs
- `mapper` – mapping between entities and DTOs
- `exception` – custom exceptions + global handler

Relations:

- `Team` 1 — N `Player`

---

## Running the project (local dev)

### 1. Start PostgreSQL with Docker

From project root:

```bash
docker-compose up -d
