#!/bin/bash

# Start Redis in the background
redis-server &

# Wait a couple seconds for Redis to start
sleep 3

# Start Spring Boot app
exec java -jar /app/app.jar
