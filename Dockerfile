# Build an image using the Java 17 image
FROM openjdk:17-jdk-alpine

# Set the directory for the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/spring-api-weather-0.0.1-SNAPSHOT.jar /app/

# Expose the port that your Spring Boot application uses
EXPOSE 8080

# Specify the commands to run when the container starts
CMD ["java", "-jar", "spring-api-weather-0.0.1-SNAPSHOT.jar"]
