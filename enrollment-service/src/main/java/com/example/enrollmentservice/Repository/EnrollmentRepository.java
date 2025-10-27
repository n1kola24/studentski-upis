package com.example.enrollmentservice.Repository;

import com.example.enrollmentservice.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository  extends JpaRepository<Enrollment,Long> {
}
