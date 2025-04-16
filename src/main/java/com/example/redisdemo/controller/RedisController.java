package com.example.redisdemo.controller;

import com.example.redisdemo.model.UserData;
import com.example.redisdemo.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/redis")
@Tag(name = "Redis API", description = "Endpoints to interact with Redis and CSV")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload CSV file and store in Redis")
    public ResponseEntity<String> uploadCSV(
            @Parameter(description = "CSV file to upload")
            @RequestParam("file") MultipartFile file) {
        try {
            redisService.saveFromCSV(file);
            return ResponseEntity.ok("Data uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get data from Redis by ID")
    public ResponseEntity<UserData> getById(@PathVariable String id) {
        UserData user = redisService.getById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}

