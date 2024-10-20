# Étape 2 : Créer l'image finale pour exécuter l'application
FROM eclipse-temurin:17.0.6_10-jdk

# Définir JAVA_HOME et l'ajouter au PATH
ENV JAVA_HOME=/usr/lib/jvm/jre-11-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /app

# Copier le fichier .jar construit à partir de l'étape précédente
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "gestion-station-skii-0.0.1-SNAPSHOT.jar"]
