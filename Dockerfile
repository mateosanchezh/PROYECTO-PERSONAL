# Instalamos el Maven
FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app

# Copiamos pom y resolvemos dependencias para cachear
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copiamos el código y corremos en modo dev
COPY src ./src
EXPOSE 8080
CMD ["mvn", "-q", "spring-boot:run"]
