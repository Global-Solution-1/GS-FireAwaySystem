FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o jar da build anterior
COPY --from=build /app/target/FireAwaySystem-1.0-SNAPSHOT.jar app.jar



RUN addgroup --system fireaway && adduser --system --ingroup fireaway fireaway
USER fireaway

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
