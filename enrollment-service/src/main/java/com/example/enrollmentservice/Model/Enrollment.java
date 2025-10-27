package com.example.enrollmentservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "enrollments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enrollment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentID;

    @Column(nullable = false)
    private String programName;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;
}
