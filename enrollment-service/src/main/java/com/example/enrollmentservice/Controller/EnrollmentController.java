package com.example.enrollmentservice.Controller;

import com.example.enrollmentservice.DTO.EnrollmentDTO;
import com.example.enrollmentservice.DTO.EnrollmentDetailsDTO;
import com.example.enrollmentservice.Model.Enrollment;
import com.example.enrollmentservice.Service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.eureka.http.RestClientEurekaHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController
{
    private final EnrollmentService service;

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody @Valid EnrollmentDTO dto)
    {
        try
        {
            EnrollmentDTO saved=service.createEnrollment(dto);
            return ResponseEntity.status(201).body(saved);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(503).body("Enrollment creation fault: "+e.getMessage());
        }

    }
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAll()
    {
        return ResponseEntity.ok(service.getAllEnrollments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable("id") Long id)
    {
        EnrollmentDTO enrollment=service.getEnrollmentByID(id);
        if(enrollment!=null)
        {
            return ResponseEntity.ok(enrollment);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(@PathVariable("id") Long id,@RequestBody @Valid EnrollmentDTO dto)
    {
        try
        {
            EnrollmentDTO updated=service.updateEnrollment(id, dto);
            if(updated==null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(503).body("Enrollment update fault: "+e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable("id") Long id)
    {
        try
        {
            boolean deleted=service.deleteEnrollment(id);
            if(!deleted) return ResponseEntity.notFound().build();
            return ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(503).body("Enrollment delete fault:"+e.getMessage());
        }
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getEnrollmentDetails(@PathVariable("id") Long id)
    {
        EnrollmentDetailsDTO details=service.getEnrollmentDetails(id);
        if(details!=null)
        {
            return ResponseEntity.ok(details);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}
