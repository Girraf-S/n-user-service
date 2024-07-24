FROM openjdk:20-jdk-slim-buster
WORKDIR /app
COPY "out/artifacts/n_user_service_jar3/n-user-service.jar" /app/n-user-service.jar
COPY "src/main/resources/application.yml" /app/application.yml
ENTRYPOINT ["java","-jar","n-user-service.jar", "--spring.config.location=file:/app/application.yml"]