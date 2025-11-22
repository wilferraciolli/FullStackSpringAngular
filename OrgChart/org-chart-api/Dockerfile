# ==============================
# STAGE 1: Build with Maven
# ==============================
# Use a Maven image with Java 25 support
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copy only the POM file first to cache dependencies
COPY pom.xml .

# Download dependencies (offline mode) to speed up future builds
# If you don't have this step, you can just COPY . . immediately,
# but this is a best practice for Docker caching.
RUN mvn dependency:go-offline -B

# Copy the actual source code
COPY src ./src

# Build the JAR file, skipping tests to save time
RUN mvn clean package -DskipTests

# ==============================
# STAGE 2: Run with Java 25
# ==============================
# Use a smaller runtime image (JRE) for production
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Create a non-root user for security (optional but recommended)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the JAR from the build stage
# The wildcards (*.jar) handle version changes in your pom.xml automatically
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]