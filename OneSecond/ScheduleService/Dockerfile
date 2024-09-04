FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/ScheduleService-0.0.1-SNAPSHOT.jar /app/ScheduleService.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/ScheduleService.jar", "-Dspring.profiles.active=prod"]
