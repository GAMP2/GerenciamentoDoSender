
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app


COPY demo/.mvn .mvn
COPY demo/mvnw demo/pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

#
COPY demo/src src
RUN ./mvnw clean package -DskipTests -B


FROM eclipse-temurin:25-jre
WORKDIR /app


RUN useradd --system --no-create-home spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]