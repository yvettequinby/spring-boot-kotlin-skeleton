# Hello World App

Demo application for the following features / tech stack:

* Spring Boot
* Kotlin
* REST API
* JWT Security (Bearer Token)
* JPA / Postgres
* Flyway
* Docker
* Micrometer (metrics)


## Requirements
* JDK 1.8+


* IntelliJ (not really, but instructions below will assume you have it)


* Docker Desktop (running)


* Basic knowledge of Spring Boot and IntelliJ IDE (how to push the green button that makes app go)


* Basic knowledge of Docker: how to start, stop, clean, etc


## Running Locally

1. Run `docker-compose up -d pg-db` from the command line. This will start the Postgres docker container.


2. Run the Spring Boot application in your IDE. This will:
   1. Automatically trigger Flyway to run DB migration scripts.
   2. Initialize DB with test user data


## Using the API (Local)

1. Use Postman to make POST request to `localhost:8080/helloworld/auth` endpoint, with JSON body:

```
{
    "email":"user@thing.com",
    "password":"user1Pass"
}
```

2. Copy the resulting token. Use Postman to make GET request to `localhost:8080/helloworld` endpoint, with the token in the Authorization header (Bearer)


## Running Locally in Docker

If you want to run the application within Docker, follow these steps:

1. Run `docker-compose up -d helloworld-local --build` from the command line. 
This will build the application docker image from the docker/Dockerfile and run both the Postgres DB container and the Application container. 


### Troubleshoot Local Docker

* Stop all webapps running outside of Docker (they might be occupying the port)
* Stop all running Docker containers: `docker kill $(docker ps -q)`
* Rebuild the Docker image: `docker-compose build helloworld-local --no-cache`


## Building The Application Docker Image

`docker-compose build helloworld --no-cache`


## Application Security

This application use JWT Authentication, based partly on [this example](https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970).

It's a very basic implementation.

See [here](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter) for more detail on configuring Spring Boot security.


## Actuator Endpoints

Application is configured with the following actuator endpoints:

* /actuator/health
* /actuator/info
* /actuator/metrics

The application is further configured with micrometer. Metrics can be accessed here:

* /helloworld/actuator/metrics/<<metric-name-goes-here>>

The /helloworld GET endpoint (HelloController) includes example usage of micrometer's  @Timed. 
After hitting the endpoint, you can view the metrics by making a GET request to:

* localhost:8080/helloworld/actuator/metrics/hello-world-endpoint

The same endpoint uses HelloService, which includes an example of a custom micrometer counter.
You can view the metrics by making a GET request to:

* localhost:8080/helloworld/actuator/metrics/hello-custom-counter

## TODO

* Unit Tests
* Logging
* Metrics: test exporting to Datadog
