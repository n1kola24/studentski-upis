package com.example.studentservice.Service;

import com.example.studentservice.DTO.StudentDTO;
import com.example.studentservice.Repository.StudentRepository;
import com.example.studentservice.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    private StudentDTO mapToDTO(Student student)
    {
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .indexNumber(student.getIndexNumber())
                .email(student.getEmail())
                .phone(student.getPhone())
                .build();

    }
    public List<StudentDTO> getAllStudents()
    {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public StudentDTO getStudentByID(Long id)
    {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }
    public StudentDTO createStudent(StudentDTO studentDTO)
    {
        Student student=Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .indexNumber(studentDTO.getIndexNumber())
                .email(studentDTO.getEmail())
                .phone(studentDTO.getPhone())
                .build();
        Student save=repository.save(student);
        return mapToDTO(save);
    }
    public StudentDTO updateStudent(Long id,StudentDTO studentDTO)
    {
        return repository.findById(id).map(student -> {
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setIndexNumber(studentDTO.getIndexNumber());
            student.setEmail(studentDTO.getEmail());
            student.setPhone(studentDTO.getPhone());
            Student student1=repository.save(student);
            return mapToDTO(student1);
        }).orElse(null);
    }
    public boolean deleteStudent(Long id) {
        return repository.findById(id)
                .map(student -> {
                    repository.delete(student);

                    return true;
                }).orElse(false);
    }
}
