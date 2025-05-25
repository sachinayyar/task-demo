# Use OpenJDK 17 base image from Docker Hub
FROM docker.io/library/eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR into the image
COPY target/tekton-spring-boot-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
