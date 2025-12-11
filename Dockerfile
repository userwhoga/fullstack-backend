# Dockerfile for Spring Boot Backend
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Gradle files
COPY build.gradle gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Build application
RUN ./gradlew build -x test

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8081

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

