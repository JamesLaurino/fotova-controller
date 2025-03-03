FROM maven:3.8.5-openjdk-17

# Copiez le fichier JAR dans le conteneur
COPY ../back-fotova/target /app

# Définissez le répertoire de travail
WORKDIR /app

# Exposer le port 8080
EXPOSE 8080

# Commande pour exécuter l'application lorsque le conteneur démarre
CMD ["java", "-jar", "spring-1.0.0-snapshot.jar"]