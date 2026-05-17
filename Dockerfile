# =============================================
# Crown Backend - Multi-stage Docker Build
# Optimized for Railway / Render deployment
# =============================================

# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# --- Stage 2: Run ---
# --- Stage 2: Run ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa 1
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto configurado por la plataforma
EXPOSE 8080

# Forzar a la JVM a respetar los límites del contenedor y limitar la memoria máxima
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx380m", "-jar", "app.jar"]
