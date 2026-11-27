FROM eclipse-temurin:25-jdk AS builder

WORKDIR /APP

COPY . .

RUN ./mvnw clean package - DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=buider /app/target/*.jar app.jar

EXPOSE 27359