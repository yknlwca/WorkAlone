# 1단계: Gradle 빌드
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# 2단계: 빌드된 애플리케이션 실행
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} workalone_backend.jar
CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "workalone_backend.jar"]
