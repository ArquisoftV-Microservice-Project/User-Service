FROM maven:3.8.7-eclipse-temurin-8 AS builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:resolve
COPY src ./src
RUN mvn clean install
FROM openjdk:8-jre-alpine
ENV JWT_SECRET=PRFT \
    SERVER_PORT=8083
WORKDIR /root/
COPY --from=builder /app/target/users-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
