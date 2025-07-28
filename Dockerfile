FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn

# Make mvnw executable
RUN chmod +x mvnw

# Copy source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Expose port
EXPOSE $PORT

# Run the application
CMD java -Dserver.port=$PORT -jar target/estudiantes-0.0.1-SNAPSHOT.jar
