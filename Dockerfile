FROM openjdk:17-buster
WORKDIR /app

# Install JavaFX
RUN apt-get update && apt-get install -y openjfx

# Copy the application JAR
COPY target/PersonalizedStudyPlanner.jar app.jar

EXPOSE 8080

# Run the application
CMD ["java", "--module-path", "/usr/share/openjfx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]
