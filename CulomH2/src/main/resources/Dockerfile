FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/CulomPostgres-0.0.1-SNAPSHOT.jar /app/CulomPostgres.jar
ENTRYPOINT ["java", "-jar", "/app/CulomPostgres.jar"]
