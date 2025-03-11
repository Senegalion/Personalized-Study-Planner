FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application code to the container
COPY . /app

# Install required libraries and Maven
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxcomposite1 \
    libxrandr2 \
    libxi6 \
    libxtst6 \
    libgdk-pixbuf2.0-0 \
    libasound2 \
    libgtk-3-0 \
    maven

# Run Maven to build the project
RUN mvn clean install

# Expose the application port
EXPOSE 8085

# Command to run the application
CMD ["mvn", "javafx:run"]
