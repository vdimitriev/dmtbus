#!/bin/bash

# Test script to verify DATABASE_URL conversion works correctly

echo "========================================"
echo "Testing Railway DATABASE_URL Conversion"
echo "========================================"
echo ""

# Simulate Railway's DATABASE_URL format
export DATABASE_URL="postgresql://testuser:testpass@testhost.railway.internal:5432/testdb"
export SPRING_PROFILES_ACTIVE=prod

echo "Simulating Railway environment:"
echo "DATABASE_URL=$DATABASE_URL"
echo "SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE"
echo ""

echo "Expected JDBC URL after conversion:"
echo "jdbc:postgresql://testuser:testpass@testhost.railway.internal:5432/testdb"
echo ""

echo "Starting application (it should convert and print the URL)..."
echo ""

# Build the application
./gradlew build -x test -q

# Run the application (will fail to connect, but will print conversion message)
timeout 10s java -jar build/libs/*.jar --spring.profiles.active=prod 2>&1 | grep -E "(Converted DATABASE_URL|datasource.url)" | head -5

echo ""
echo "========================================"
echo "If you see 'Converted DATABASE_URL to JDBC format' above, the conversion is working!"
echo "========================================"

