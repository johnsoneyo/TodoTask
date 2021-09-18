FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} todo.jar
ENTRYPOINT ["java","-jar","/todo.jar"]