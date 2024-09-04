# 베이스 이미지 지정
FROM openjdk:17-ea-11-jdk-slim
# 디렉토리 추가
WORKDIR /app
# 파일 복사
COPY build/libs/ChatService-0.0.1-SNAPSHOT.jar /app/ChatService.jar
# Port 개발
EXPOSE 8081
# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod
# 컨테이너 실행 시 기본으로 실행할 명령 : java -jar xxx.jar
ENTRYPOINT ["java", "-jar", "/app/ChatService.jar"]