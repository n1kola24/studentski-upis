package com.example.enrollmentservice.Service;

import com.example.enrollmentservice.Client.StudentClient;
import com.example.enrollmentservice.Config.RabbitMQConfig;
import com.example.enrollmentservice.DTO.EnrollmentDTO;
import com.example.enrollmentservice.DTO.EnrollmentDetailsDTO;
import com.example.enrollmentservice.DTO.StudentDTO;
import com.example.enrollmentservice.Model.Enrollment;
import com.example.enrollmentservice.Repository.EnrollmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService
{
    private final EnrollmentRepository repository;
    private final StudentClient client;
    private final RabbitTemplate rabbit;
    private EnrollmentDTO mapToDTO(Enrollment enrollment)
    {
        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .studentID(enrollment.getStudentID())
                .programName(enrollment.getProgramName())
                .semester(enrollment.getSemester())
                .status(enrollment.getStatus())
                .createdAt(enrollment.getCreatedAt())
                .build();

    }
    @Transactional
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto)
    {
        StudentDTO student=fetchStudentWithResilience(dto.getStudentID());
        if(student==null || student.getId()==null)
        {
            throw new IllegalArgumentException("Student nije pronadjen ili nije dostupan. Student ID:"+dto.getStudentID());
        }
        Enrollment enrollment=new Enrollment();
        enrollment.setStudentID(dto.getStudentID());
        enrollment.setProgramName(dto.getProgramName());
        enrollment.setSemester(dto.getSemester());
        enrollment.setStatus(dto.getStatus());
        enrollment.setCreatedAt(dto.getCreatedAt());

        Enrollment saved=repository.save(enrollment);
        try
        {
            rabbit.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    "Enrollment created ID: "+saved.getId() + " StudentID: "+saved.getStudentID()+" Program name: "+saved.getProgramName()+" Semester:"+saved.getSemester()+" Status: "+ saved.getStatus()+" CreatedAt: "+saved.getCreatedAt()
            );
            System.out.println("Event poslat u RabbitMQ: Enrollment created for EnrollmentID"+ saved.getId());
        }
        catch (Exception e)
        {
            System.err.println("Nije moguce poslati event RabbitMQ: "+e.getMessage());
        }
        EnrollmentDTO pom=mapToDTO(saved);
        return pom;
    }
    public List<EnrollmentDTO> getAllEnrollments()
    {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public EnrollmentDTO getEnrollmentByID(Long id)
    {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }
    @Transactional
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto)
    {
        return repository.findById(id)
                .map(enrollment->
                {
                    enrollment.setStudentID(dto.getStudentID());
                    enrollment.setProgramName(dto.getProgramName());
                    enrollment.setSemester(dto.getSemester());
                    enrollment.setStatus(dto.getStatus());
                    enrollment.setCreatedAt(dto.getCreatedAt());
                    return mapToDTO(repository.save(enrollment));
                }).orElse(null);
    }
    @Transactional
    public boolean deleteEnrollment(Long id)
    {
        return repository.findById(id).map(enrollment ->
        {
            repository.delete(enrollment);
            return true;
        }).orElse(false);
    }

    public EnrollmentDetailsDTO getEnrollmentDetails(Long id)
    {
        return repository.findById(id).map(enrollment ->
        {
            StudentDTO student;
            try
            {
                student=fetchStudentWithResilience(enrollment.getStudentID());
            }
            catch (Exception e)
            {
                System.out.println("Student service nije dostupan: "+ e.getMessage());
                student=studentFallback(enrollment.getStudentID(),e);
            }
            EnrollmentDetailsDTO enrollmentDTO=new EnrollmentDetailsDTO();
            enrollmentDTO.setStudentID(enrollment.getStudentID());
            enrollmentDTO.setProgramName(enrollment.getProgramName());
            enrollmentDTO.setSemester(enrollment.getSemester());
            enrollmentDTO.setStatus(enrollment.getStatus());
            enrollmentDTO.setCreatedAt(enrollment.getCreatedAt());

            enrollmentDTO.setFirstName(student.getFirstName());
            enrollmentDTO.setLastName(student.getLastName());
            enrollmentDTO.setIndexNumber(student.getIndexNumber());
            enrollmentDTO.setEmail(student.getEmail());
            enrollmentDTO.setPhone(student.getPhone());

            return enrollmentDTO;


        }).orElse(null);
    }
    // --- Resilience4j Feign call ---
    @CircuitBreaker(name = "studentServiceCB", fallbackMethod = "studentFallback")
    @Retry(name = "studentServiceRetry")
    public StudentDTO fetchStudentWithResilience(Long studentID) {
        return client.getStudentByID(studentID);
    }

    // fallback method
    public StudentDTO studentFallback(Long studentID, Throwable t) {
        System.out.println("⚠️ Fallback aktiviran za userId: " + studentID + " | Razlog: " + t.getMessage());


        StudentDTO fallback = new StudentDTO();
        fallback.setId(studentID);
        fallback.setFirstName("IME");
        fallback.setLastName("PREZIME");
        fallback.setIndexNumber("INDEKS");
        fallback.setEmail("EMAIL");
        fallback.setPhone("TELEFON");
        return fallback;
    }
}
