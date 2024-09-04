FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/GatewayService-0.0.1-SNAPSHOT.jar /app/GatewayService.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "/app/GatewayService.jar"]