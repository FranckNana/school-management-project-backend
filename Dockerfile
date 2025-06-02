FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw && ./mvnw clean verify -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]
