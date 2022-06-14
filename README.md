# Hello World App

Demo application for the following features / tech stack:

* Spring Boot
* Kotlin
* REST API
* JWT Security (Bearer Token)
* JPA / Postgres
* Flyway
* Docker


## Requirements
* JDK 1.8+


* IntelliJ (not really, but instructions below will assume you have it)


* Docker Desktop


* Basic knowledge of Spring Boot and IntelliJ IDE (how to push the green button that makes app go)


* Basic knowledge of Docker: how to start, stop, clean, etc


## Running Locally

1. Run `docker-compose up` from the command line. This will start the Postgres docker container.


2. Edit the Spring Boot application run configuration (in IntelliJ): set the "active profiles" value to "local"


3. Run the Spring Boot application in your IDE. This will:
   1. Automatically trigger Flyway to run DB migration scripts.
   2. Initialize DB with test user data


4. Use Postman to make POST request to `/helloworld/auth` endpoint, with JSON body:

```
{
    "email":"user@thing.com",
    "password":"user1Pass"
}
```

3. Copy the resulting token. Use Postman to make GET request to `/helloworld` endpoint, with the token in the Authorization header (Bearer)


## Security

Uses JWT Authentication, based partly on [this example](https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970).

See [here](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter) for more detail on configuring Spring Boot security.

NOT FOR PRODUCTION - DEMO FEATURES

* JWT secret is currently hard-coded. This should be an env variable, exposed via K8s secrets


* Users/Passwords currently hard-coded and in memory. Should be replaced with DB storage.