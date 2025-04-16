package com.example.redisdemo.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public Health health() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            String ping = connection.ping();
            if ("PONG".equals(ping)) {
                return Health.up()
                        .withDetail("message", "Redis is up and running")
                        .build();
            }
            return Health.down()
                    .withDetail("message", "Redis ping failed")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("message", "Redis connection failed")
                    .withException(e)
                    .build();
        }
    }
} 