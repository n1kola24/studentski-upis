package com.example.enrollmentservice.Client;

import com.example.enrollmentservice.DTO.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentClient
{
    @GetMapping("/api/students/{id}")
    StudentDTO getStudentByID(@PathVariable("id") Long id);
}
