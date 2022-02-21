FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-employees-module-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/spring-employees-module-api-0.0.1-SNAPSHOT.jar"]