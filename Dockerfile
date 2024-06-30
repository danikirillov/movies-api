FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

WORKDIR /usr/src/app
COPY ./ .

RUN mvn clean package -T 2C -DskipTests
RUN mv "./target/movies-api-$(mvn help:evaluate -Dexpression='project.version' -q -DforceStdout).jar" ./target/movies-api.jar

FROM eclipse-temurin:21.0.3_9-jre-alpine

EXPOSE 8080
#Hack to create all needed folders with correct user permissions
WORKDIR /app
WORKDIR /tmp
WORKDIR /static
WORKDIR /
COPY --from=builder /usr/src/app/target/movies-api.jar /app/
ENTRYPOINT ["java", "-jar", "/app/movies-api.jar"]