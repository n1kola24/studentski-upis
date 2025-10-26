package com.example.enrollmentservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "student-service")
public interface StudentClient
{
    @GetMapping("/api/students/{id}")
}
