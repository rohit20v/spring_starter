FROM eclipse-temurin:21

WORKDIR /app

COPY target/spring_starter-0.0.1-SNAPSHOT.jar /app/myspringapp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/myspringapp.jar"]