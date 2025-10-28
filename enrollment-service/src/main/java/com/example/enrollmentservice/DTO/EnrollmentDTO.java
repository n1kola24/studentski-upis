package com.example.enrollmentservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class EnrollmentDTO
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "studentID je obavezan")
    private Long studentID;
    @NotBlank(message = "Ime programa je obavezno")
    private String programName;

    @NotBlank(message = "Semestar je obavezan")
    private String semester;

    private String status;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date createdAt;

}
