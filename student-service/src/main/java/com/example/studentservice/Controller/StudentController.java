package com.example.studentservice.Controller;

import com.example.studentservice.DTO.StudentDTO;
import com.example.studentservice.Service.StudentService;
import jakarta.validation.Valid;
import lombok.Value;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController
{
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents()
    {
        List<StudentDTO> students=service.getAllStudents();
        if(students!=null)
        {
            return ResponseEntity.ok(students);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id)
    {
        StudentDTO student=service.getStudentByID(id);
        if(student!=null)
        {
            return ResponseEntity.ok(student);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO)
    {
        StudentDTO student=service.createStudent(studentDTO);
        if(student!=null)
        {
            return ResponseEntity.ok(student);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO)
    {
        StudentDTO student=service.updateStudent(id,studentDTO);
        if(student!=null)
        {
            return ResponseEntity.ok(student);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable Long id)
    {
        if(service.deleteStudent(id))
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
