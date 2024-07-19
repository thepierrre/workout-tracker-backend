# Use an image with Maven and JDK installed
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a different base image for running the application
FROM registry.access.redhat.com/ubi9/openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the previous stage
COPY --from=build /app/target/workout-tracker-service-*.jar workout-tracker-service.jar

# Switch to a non-root user
USER 1001

# Set the entrypoint to use the jar file
ENTRYPOINT ["java", "-jar", "workout-tracker-service.jar"]



# FROM registry.access.redhat.com/ubi9/openjdk-21

# Switch to root user temporarily
# USER root

# Copy the jar file to the container
# COPY target/workout-tracker-service-*.jar workout-tracker-service.jar

# Set environment variable to store the version from pom.xml
#ENV APP_VERSION="1.3.6"

# Create a directory for the script and copy it with correct permissions
#RUN mkdir -p /usr/local/bin
#COPY extract_version.sh /usr/local/bin/extract_version.sh
#RUN chmod +x /usr/local/bin/extract_version.sh

# Switch back to a non-root user
# USER 1001

# Set the entrypoint to use the dynamically determined jar name
#ENTRYPOINT ["/bin/bash", "-c", "/usr/local/bin/extract_version.sh && java -jar demo-app-${APP_VERSION}.jar"]
# ENTRYPOINT ["/bin/bash", "-c", "java -jar workout-tracker-service.jar"]