# Student Management System

A minimal Java web application demonstrating an end-to-end CI/CD pipeline using
Git, Maven, Docker and Jenkins.

## Operations

| Method | Path              | Description        |
|--------|-------------------|--------------------|
| GET    | `/students`       | View all students  |
| GET    | `/add?name=Asha`  | Add a student      |
| POST   | `/students`       | Add a student      |

## Build and test

```
mvn clean package
```

Produces `target/student-app.jar` and runs the unit tests.

## Run with Docker

```
docker build -t student-app:latest .
docker run -d --name student-app -p 8081:8080 student-app:latest
```

The application is then available at http://localhost:8081

## CI/CD

`Jenkinsfile` defines a four-stage pipeline: Checkout, Build & Test,
Docker Build, and Deploy.


#Note

Search feature under development on feature-search
