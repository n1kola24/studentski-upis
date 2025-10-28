package com.example.enrollmentservice.Test;

import com.example.enrollmentservice.Controller.EnrollmentController;
import com.example.enrollmentservice.DTO.EnrollmentDTO;
import com.example.enrollmentservice.Service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EnrollmentController.class)
public class EnrollmentWebMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EnrollmentService service;

    @Test
    void createEnrollment_valid_returns201() throws Exception {
        Mockito.when(service.createEnrollment(any(EnrollmentDTO.class)))
                .thenAnswer(inv -> {
                    EnrollmentDTO e = inv.getArgument(0);
                    e.setId(1L);
                    return e;
                });

        String body = """
          {
            "studentID": 1,
            "programName": "Racunarska i informatika",
            "semester": "ZIMSKI",
            "status": "ENROLLED",
            "createdAt": "27.10.2025"
          }
        """;

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createEnrollment_invalidDate_returns400() throws Exception {
        // Pogresan Datum
        String badBody = """
          {
            "studentID": 1,
            "programName": "Racunarska i informatika",
            "semester": "ZIMSKI",
            "status": "ENROLLED",
            "createdAt": "2025-10-27"
          }
        """;

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badBody))
                .andExpect(status().isBadRequest());
    }
}
