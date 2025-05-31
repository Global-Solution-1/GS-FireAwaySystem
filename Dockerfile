# Stage 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: runtime com imagem slim
FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY --from=build /app/target/fireaway-api.jar app.jar

RUN addgroup --system fireaway && adduser --system --ingroup fireaway fireaway
USER fireaway

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
