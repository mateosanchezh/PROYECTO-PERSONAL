
########## STAGE 1: BUILD ##########
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace/app

# 1) Copiamos el POM y resolvemos dependencias para cachear .m2
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q -DskipTests dependency:go-offline

# 2) Copiamos el código y compilamos
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q -DskipTests package

########## STAGE 2: RUNTIME ##########
FROM eclipse-temurin:21-jre-alpine
# Usuario no root
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app

# Copiamos el JAR construido
COPY --from=build /workspace/app/target/*.jar /app/app.jar

USER spring
EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"

ENTRYPOINT ["java","-jar","/app/app.jar"]
