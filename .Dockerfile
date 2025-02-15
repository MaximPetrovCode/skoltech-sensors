FROM openjdk:8-jdk-alpine
VOLUME /libs
EXPOSE 9090
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD build/libs/task-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]