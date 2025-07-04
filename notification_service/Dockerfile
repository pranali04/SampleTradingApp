# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar

EXPOSE 8083

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "build/libs/notification_service-0.0.1-SNAPSHOT.jar"]
