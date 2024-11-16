
FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY . /app
WORKDIR /app/MyChoiceApp/MyChoiceApp
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/MyChoiceApp/MyChoiceApp/target/MyChoiceApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 5000

CMD ["java", "-jar", "app.jar"]

