package com.example.redisdemo.service;

import com.example.redisdemo.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, UserData> redisTemplate;

    public String saveFromCSV(MultipartFile file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            UserData user = new UserData(fields[0], fields[1], fields[2]);
            redisTemplate.opsForValue().set(user.getId(), user);
        }
        return "CSV Loaded Successfully!";
    }

    public UserData getById(String id) {
        return redisTemplate.opsForValue().get(id);
    }

    public long countRecords() {
        return Optional.ofNullable(redisTemplate.keys("*")).map(Set::size).orElse(0);
    }
}
