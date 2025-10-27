package com.example.enrollmentservice.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO
{
    private Long id;
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String email;
    private String phone;
}
