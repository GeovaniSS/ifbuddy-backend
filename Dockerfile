# ======================================================================
# Estágio 1: Build da Aplicação
# ======================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package

# ======================================================================
# Estágio 2: Imagem Final de Execução
# ======================================================================
FROM openjdk:21-slim

WORKDIR /app

EXPOSE 8080

# Copia o JAR correto do estágio de build e renomeia para app.jar
COPY --from=build /app/target/*.jar app.jar

# Executa o app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]