FROM azul/zulu-openjdk:17

# Install Redis
RUN apt-get update && \
    apt-get install -y redis-server && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Set workdir
WORKDIR /app

# Copy built JAR with correct name
COPY target/redisdemo-0.0.1-SNAPSHOT.jar app.jar

# Copy the startup script
COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chmod +x /app/docker-entrypoint.sh

# Expose ports
EXPOSE 8080 6379

ENTRYPOINT ["/app/docker-entrypoint.sh"]
