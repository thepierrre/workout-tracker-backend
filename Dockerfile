FROM registry.access.redhat.com/ubi9/openjdk-21

# Switch to root user temporarily
USER root

# Copy the jar file to the container
COPY target/workout-tracker-service-*.jar workout-tracker-service.jar

# Set environment variable to store the version from pom.xml
#ENV APP_VERSION="1.3.6"

# Create a directory for the script and copy it with correct permissions
#RUN mkdir -p /usr/local/bin
#COPY extract_version.sh /usr/local/bin/extract_version.sh
#RUN chmod +x /usr/local/bin/extract_version.sh

# Switch back to a non-root user
USER 1001

# Set the entrypoint to use the dynamically determined jar name
#ENTRYPOINT ["/bin/bash", "-c", "/usr/local/bin/extract_version.sh && java -jar demo-app-${APP_VERSION}.jar"]
ENTRYPOINT ["/bin/bash", "-c", "java -jar workout-tracker-service.jar"]