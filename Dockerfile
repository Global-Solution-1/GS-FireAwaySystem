# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/fireaway-api.jar app.jar

# User sem root
RUN addgroup --system fireaway && adduser --system --ingroup fireaway fireaway
USER fireaway

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
