FROM openjdk:17-ea-11-jdk-slim
ENV TZ="Asia/Seoul"
WORKDIR /app
COPY build/libs/InvitationService-0.0.1-SNAPSHOT.jar /app/InvitationService.jar
EXPOSE 8090
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "/app/InvitationService.jar"]
