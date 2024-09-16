# Define the base image for the build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

# Download the Maven dependencies and store them in the local Maven repository
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package

# Use a different base image for running the application
FROM registry.access.redhat.com/ubi9/openjdk-17

WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/workout-tracker-service-*.jar workout-tracker-service.jar

USER 1001

# ENTRYPOINT ["java", "-jar", "workout-tracker-service.jar"]

# CMD export POSTGRES_PASSWORD=$(cat /run/secrets/postgres_pwd) && \
#     export DUMMYUSER_PASSWORD=$(cat /run/secrets/dummyuser_pwd) && \
#     java -jar workout-tracker-service.jar


# Set the entrypoint to use the dynamically determined jar name
#ENTRYPOINT ["/bin/bash", "-c", "/usr/local/bin/extract_version.sh && java -jar demo-app-${APP_VERSION}.jar"]