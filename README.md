# OGC Nice API

API REST backend développée avec Spring Boot pour gérer les équipes de l’OGC Nice et leurs joueurs.

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

Structure des packages :

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

# Exécution du projet

Vous pouvez exécuter l’application de **deux manières**:

---

# 1️⃣ Développement local (Maven + PostgreSQL local via Docker)

### Démarrer PostgreSQL avec Docker

```bash
docker-compose up -d postgres
```

### Lancer l’application

```bash
mvn spring-boot:run
```

API disponible à l’adresse :
```
http://localhost:8080
```

---

# 2️⃣ Application entièrement dockerisée (recommandé)


L’ensemble de la stack (API + PostgreSQL) peut être démarré avec une **seule commande**:

```bash
docker-compose up --build
```

Cela va :
Construire le JAR Spring Boot
Construire l’image Docker à partir du Dockerfile
Démarrer PostgreSQL
Démarrer le conteneur de l’API
Exposer l’API sur le port **8080**

### Test:

```bash
curl http://localhost:8080/api/teams
```

---

# Détails de la configuration Docker

### Dockerfile (build multi-étapes)


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
Prend en charge la pagination et le tri.

```
GET /api/teams?page=0&size=10&sortBy=name&direction=asc
```

---

### GET /api/teams/{id}

Renvoie l’équipe avec ses joueurs ou **404 Not Found**.

---

### DELETE /api/teams/{id}

Supprime une équipe et renvoie **204 No Content**.

---

# Error handling

Géré globalement :

- 400 → Erreurs de validation
- 404 → Équipe introuvable
- 500 → Toutes les autres exceptions
---

# Tests

Les tests utilisent **H2** et le profil **test**.
Exécution en local :

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

La CI s’exécute à chaque push et pull request :

- Configuration de Java 17
- Mise en cache de Maven
- Run `mvn verify` (build + tests)

---

# Commandes utiles

**Démarrer la stack complète (recommandé) :**

```bash
docker-compose up --build
```

**Démarrer uniquement PostgreSQL :**

```bash
docker-compose up -d postgres
```

**Lancer l’application en local :**

```bash
mvn spring-boot:run
```

**Build project:**

```bash
mvn clean install
```

---

# État du projet
- Backend opérationnel avec :
- Opérations CRUD
- Pagination et tri
- Relations entre entités
- Couche Mapper
- DTOs
- Exceptions personnalisées
- Gestionnaire global des erreurs
- Pipeline CI
- Support Docker complet

