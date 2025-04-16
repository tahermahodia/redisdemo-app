package com.example.redisdemo.scheduler;

import com.example.redisdemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RedisCountScheduler {

    @Autowired
    private RedisService redisService;

    @Scheduled(fixedRate = 600000) // every 10 minutes
    public void logRedisRecordCount() {
        long count = redisService.countRecords();
        System.out.println("Redis record count: " + count);
    }
}