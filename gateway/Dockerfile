FROM eclipse-temurin:17-jre-alpine

ARG APP_NAME

WORKDIR /app
COPY ${APP_NAME}.jar /app/app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar" ]