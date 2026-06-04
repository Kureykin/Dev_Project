FROM eclipse-temurin:25-jdk
COPY build/libs/app-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080