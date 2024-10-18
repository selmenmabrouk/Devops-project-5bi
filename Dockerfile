# Étape 1 : Build l'application avec Maven
FROM maven:3.9.0-eclipse-temurin-17
WORKDIR /app
COPY . .

# Construire le package avec Maven
RUN mvn clean install

# Étape 2 : Créer l'image finale pour exécuter l'application
FROM eclipse-temurin:17.0.6_10-jdk
WORKDIR /app

# Copier le fichier .jar construit à partir de l'étape précédente
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "gestion-station-skii-0.0.1-SNAPSHOT.jar"]

