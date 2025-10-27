package com.example.enrollmentservice.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    private long id;

    @NotNull(message = "studentID je obavezan")
    private Long studentID;
    @NotBlank(message = "Ime programa je obavezno")
    private String programName;

    @NotBlank(message = "Semestar je obavezan")
    private String semester;

    private String status;
    private Date createdAt;

}
