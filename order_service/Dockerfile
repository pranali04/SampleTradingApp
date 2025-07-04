FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x gradlew
RUN ./gradlew bootJar
# RUN ./gradlew build -x test

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/order_service-0.0.1-SNAPSHOT.jar"]
