FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8888
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/university-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]