package com.example.studentservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String indexNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;


}
