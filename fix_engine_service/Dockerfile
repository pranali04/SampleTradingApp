# ✅ 1. Use a stable Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# ✅ 2. Set working directory
WORKDIR /app

# ✅ 3. Copy Gradle wrapper + project files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# ✅ 4. Make gradlew executable
RUN chmod +x gradlew

# ✅ 5. Build the Spring Boot JAR
RUN ./gradlew clean build -x test

# ✅ 6. Expose the default FIX port (e.g., 9876) & app port if you have one
EXPOSE 5001

# ✅ 7. Run the application JAR
CMD ["java", "-jar", "build/libs/fix_engine_service-0.0.1-SNAPSHOT.jar"]
