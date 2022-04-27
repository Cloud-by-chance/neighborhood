FROM openjdk:11

#빌드 되는 시점에 JAR_FILE 경로
ARG JAR_FILE=build/libs/*.jar

#빌드된 JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]


