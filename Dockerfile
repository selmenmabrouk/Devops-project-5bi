# Use Maven for building the project
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Use JDK for running the app
FROM eclipse-temurin:17.0.6_10-jdk
WORKDIR /app
COPY --from=build /app/target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "gestion-station-skii-0.0.1-SNAPSHOT.jar"]
