FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o jar da build anterior
COPY --from=build /app/target/FireAwaySystem-1.0-SNAPSHOT.jar app.jar

# Baixa o wait-for-it.sh e dá permissão para execução
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN curl -o wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Cria usuário para rodar a aplicação
RUN addgroup --system fireaway && adduser --system --ingroup fireaway fireaway
USER fireaway

EXPOSE 8080

# Espera o oracle-db na porta 1521 estar disponível e só depois executa o java
ENTRYPOINT ["./wait-for-it.sh", "oracle-db:1521", "--", "java", "-jar", "app.jar"]
