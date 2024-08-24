FROM openjdk:21-jdk-slim
# Install curl
RUN apt-get update && apt-get install -y curl
WORKDIR /app
COPY target/*.jar  /app/app.jar
ENTRYPOINT ["java","-jar", "app.jar"]