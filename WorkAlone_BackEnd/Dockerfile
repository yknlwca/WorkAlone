# 1단계: Gradle 빌드
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app
COPY . .
ARG SPRING_PROFILES_ACTIVE
RUN ./gradlew clean build

# 2단계: 빌드된 애플리케이션 실행
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} workalone_backend.jar
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "-Duser.timezone=Asia/Seoul", "workalone_backend.jar"]
