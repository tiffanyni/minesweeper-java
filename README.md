# Shoopy's Minesweeper!!!

## Goal:
Our goal is to recreate the familiar minesweeper game from scratch using Java Spring Boot.

## Features:
- Playable Minesweeper board with 3 difficulty levels (easy/medium/hard)
- User authentication (register + login)
- Save & resume game progress
- Server-rendered UI with Thymeleaf

## Stack:
### Backend:
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven

### Frontend:
- Thymeleaf
- HTML/CSS

### Database:
- H2 (for local development; file-based)
- PostgreSQL (planned)

## Architecture:
We're following a standare layered Spring Boot architecture:

`Controller -> Service -> Repository -> Database`

- Controller handles HTTP requests and routing
- Service contains business logic
- Repository manages database interactions
- Model represents core data structures (User, Cell, etc)
- DTO handles request & response data (Login, Register, etc)

## Current Progress:


## How to Run:
1. Clone the repo

2. Run the app

  `./mvnw spring-boot:run`

3. Open in browser

  `http://localhost:8080`


