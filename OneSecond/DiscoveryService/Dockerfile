FROM openjdk:17-ea-11-jdk-slim
WORKDIR /app
COPY build/libs/DiscoveryService-0.0.1-SNAPSHOT.jar /app/DiscoveryService.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/DiscoveryService.jar"]