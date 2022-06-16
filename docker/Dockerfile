FROM openjdk:17 AS build
WORKDIR /workspace/app
COPY ../mvnw .
COPY ../.mvn .mvn
COPY ../pom.xml .
COPY ../src src
RUN ./mvnw install -DskipTests


FROM openjdk:17
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]