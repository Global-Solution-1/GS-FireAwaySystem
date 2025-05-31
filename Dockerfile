# Stage 1: Build da aplicação com Maven
FROM maven:3.8.7-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Imagem final só com JRE e o jar
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/fireaway-api.jar app.jar

EXPOSE 8080

RUN useradd -m fireawayuser
USER fireawayuser

ENTRYPOINT ["java", "-jar", "app.jar"]
