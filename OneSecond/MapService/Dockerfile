FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/MapService-0.0.1-SNAPSHOT.jar /app/PlaceService.jar
EXPOSE 8088
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "/app/PlaceService.jar"]