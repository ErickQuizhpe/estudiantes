#!/bin/bash
# Build script for Render deployment

# Set JAVA_HOME if not set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/opt/java/openjdk
fi

# Make mvnw executable
chmod +x ./mvnw

# Run the build
./mvnw clean package -DskipTests
