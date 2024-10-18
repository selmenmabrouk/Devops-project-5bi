# Use a different base image
FROM openjdk:17-jdk-slim

# Create a directory for your application
WORKDIR /app

# Copy your JAR file into the container
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/gestion-station-skii.jar

# Expose the port your application runs on (change if necessary)
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "/app/gestion-station-skii.jar"]
