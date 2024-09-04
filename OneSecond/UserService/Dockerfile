FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/UserService-0.0.1-SNAPSHOT.jar /app/UserService.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app/UserService.jar", "-Dspring.profiles.active=prod"]
