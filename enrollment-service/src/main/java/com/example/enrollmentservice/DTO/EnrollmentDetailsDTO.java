package com.example.enrollmentservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollmentDetailsDTO
{
    private long id;
    private String programName;
    private String semester;
    private String status;
    private Date createdAt;
    private Long studentID;
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String email;
    private String phone;

}
