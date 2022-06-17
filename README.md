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
* Kubernetes / K8s


## Requirements
* JDK 1.8+


* IntelliJ (not really, but instructions below will assume you have it)


* Docker Desktop, with Kubernetes Enabled


* Kubectl


* A Dockerhub account and repo (if you want to do the K8s deployment step)


* Postman (not really, but instructions below will assume you have it)


* Basic knowledge of Spring Boot and IntelliJ IDE (how to push the green button that makes app go)


* Basic knowledge of Docker: how to start, stop, clean, etc


## Running Locally

1. Run `docker-compose up -d pg-db` from the command line. This will start the Postgres docker container.


2. Run the Spring Boot application in your IDE. This will:
   1. Automatically trigger Flyway to run DB migration scripts.
   2. Initialize DB with test user data
   3. Start the application running on localhost:8080


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


## Deploying To (Local) Kubernetes

This section is a very simplified example of deploying to a local kubernetes cluster. 
It is based on the tutorials available [here](https://kubebyexample.com/en/learning-paths/developing-spring-boot-kubernetes).

_Requirements_:
* Kubernetes enabled on your Docker Desktop (open Docker Desktop / preferences to enable)
* Kubectl installed
* A Dockerhub account and repo for this project

First, make sure your local database is running. 

`docker-compose up -d pg-db`


Next, login to your Dockerhub account from the command line:

`docker login`

Next, build the Docker image:

`docker-compose build helloworld --no-cache`

Tag the built image to match your Dockerhub account and repo:

`docker tag helloworld_helloworld <<YOUR ACCOUNT>>/<<YOUR REPO>>:latest`

Push the image to your docker repo:

`docker push <<YOUR ACCOUNT>>/<<YOUR REPO>>:latest`

Verify the Kubernetes environment is enabled in your local Docker Desktop
by running the following commands:

```
kubectl config get-contexts
kubectl config use-context docker-desktop
kubectl get nodes
kubectl get all
```

The commands should allow you to see the Docker Desktop K8s cluster and the single node running in the cluster.

Generate a deployment.yml by running the following command:

`kubectl create deployment helloworld --image <<YOUR ACCOUNT>>/<<YOUR REPO>> --dry-run=client -o=yaml > deployment.yml`

deployment.yml will be created in the project root directory.

You must now add the SPRING_DATASOURCE_URL environment variable, so that the application can connect to the database running in Docker (outside K8s).
Edit the deployment.yml and add `env` section to the `spec/containers`:

```
spec:
   containers:
   - image: xxx/zzz
     name: yyy
     resources: {}
     env:
       - name: SPRING_DATASOURCE_URL
         value: jdbc:postgresql://host.docker.internal:5432/helloworld
```

Now deploy the application to K8s:

`kubectl apply -f deployment.yml`

Verify the deployment by running:

`kubectl get all`

Now generate the service.yml:

`kubectl create service nodeport helloworld --tcp=8080:8080 --dry-run=client -o=yaml > service.yml`

service.yml will be created in the project root directory.

Now create the service in K8s:

`kubectl apply -f service.yml`

Verify the service creation by running:

`kubectl get all`

You should now be able to access the endpoints. You must use the port mapping shown for the service.
For example:

```
xxx@DEM0164 helloworld % kubectl get all

NAME                              READY   STATUS    RESTARTS   AGE
pod/helloworld-6b95496dc8-kd85z   1/1     Running   0          5m20s

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
service/helloworld   NodePort    10.102.148.51   <none>        8080:30485/TCP   8s
```
Would require a GET call to localhost:30485/helloworld/actuator/health

The port number changes with each deployment.

### Access K8s Logs
kubectl logs --tail=20 << POD NAME >>

### Terminate Service and Deployment

```
kubectl delete service helloworld
kubectl delete deployment helloworld
```

## Application Security

This application use JWT Authentication, based partly on [this example](https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970).

It's a very basic implementation.

See [here](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter) for more detail on configuring Spring Boot security.


## Actuator Endpoints

Application is configured with the following actuator endpoints:

* /actuator/health
* /actuator/info
* /actuator/metrics


## Metrics

In addition to standard Actuator metrics, the application configured with micrometer. 
Individual metrics are accessed via an endpoint like:

* GET /helloworld/actuator/metrics/<<metric-name-goes-here>>

The /helloworld GET endpoint (HelloController) includes example usage of micrometer's @Timed annotation. 
After hitting the endpoint, you can view the metrics by making a GET request to:

* localhost:8080/helloworld/actuator/metrics/hello-world-endpoint

The same HelloController makes a call to HelloService, which includes an example of a custom micrometer counter.
You can view the metrics for this custom counter by making a GET request to:

* localhost:8080/helloworld/actuator/metrics/hello-custom-counter


## Logging

Logging uses SLF4j. See examples in HelloService.

See application-local.yml for example on overriding default log levels.




## TODO

* Unit Tests
* Logging: test exporting to Datadog
* Metrics: test exporting to Datadog
