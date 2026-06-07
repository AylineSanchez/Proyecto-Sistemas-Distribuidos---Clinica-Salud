# Paso 1: Usar una imagen base de Maven con Java 21 (Esta sí existe)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Paso 2: Usar una imagen liviana de ejecución con Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/servicio-bodega-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]