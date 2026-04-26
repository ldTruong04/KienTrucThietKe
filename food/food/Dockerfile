# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY target/food-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application runs on (default Spring Boot port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]