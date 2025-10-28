package com.example.studentservice.Test;

import com.example.studentservice.Controller.StudentController;
import com.example.studentservice.DTO.StudentDTO;
import com.example.studentservice.Service.StudentService;
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

@WebMvcTest(controllers = StudentController.class)
public class StudentWebMVCTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService service;

    @Test
    void createStudent_valid() throws Exception {
        Mockito.when(service.createStudent(any(StudentDTO.class)))
                .thenAnswer(inv -> {
                    StudentDTO s = inv.getArgument(0);
                    s.setId(1L);
                    return s;
                });

        String body = """
          {"firstName":"Ana","lastName":"Jovanovic","indexNumber":"55/2022",
           "email":"ana@example.com","phone":"061234567"}
        """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createStudent_invalid() throws Exception {
        // Pogresan format indeksa i telefona
        String badBody = """
          {"firstName":"Ana","lastName":"Jovanovic","indexNumber":"2022-55",
           "email":"ana@example.com","phone":"06A234567"}
        """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badBody))
                .andExpect(status().isBadRequest());

    }
}
