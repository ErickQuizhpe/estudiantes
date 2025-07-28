#!/bin/bash
# Build script for Render deployment

# Set JAVA_HOME if not set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/opt/java/openjdk
fi

# Try with system Maven first, fallback to mvnw
if command -v mvn &> /dev/null; then
    echo "Using system Maven..."
    mvn clean package -DskipTests
else
    echo "Using Maven wrapper..."
    chmod +x ./mvnw
    ./mvnw clean package -DskipTests
fi
