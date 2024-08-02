FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/*.jar /app/ecommerce-app.jar
ENTRYPOINT ["java", "-jar", "/app/ecommerce-app.jar"]
