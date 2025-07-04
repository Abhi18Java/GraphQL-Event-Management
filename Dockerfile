FROM eclipse-temurin:17-jre

WORKDIR /app

EXPOSE 8080
COPY wait-for-it.sh wait-for-it.sh
COPY target/graphql-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]