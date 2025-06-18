FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean verify -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

COPY src/main/resources/firebase/serviceAccountKey.json /app/firebase/serviceAccountKey.json

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]