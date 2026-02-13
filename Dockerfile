# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build -x test

# Copy the built JAR to app.jar
RUN mv build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application with production profile
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
