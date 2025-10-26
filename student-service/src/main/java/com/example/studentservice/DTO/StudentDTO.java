package com.example.studentservice.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO
{
    private Long id;

    @NotBlank(message = "Ime ne sme biti prazno")
    @Size(min = 2,max = 20,message = "Ime mora biti izmedju 2 i 20 karaktera")
    private String firstName;

    @NotBlank(message = "Prezime ne sme biti prazno")
    @Size(min = 2,max = 20,message = "Prezime mora biti izmedju 2 i 20 karaktera")
    private String lastName;

    @NotBlank(message = "Broj indeksa je obavezan")
    @Pattern(regexp = "^[0-9]{2,}/[0-9]{4}$", message = "Format indeksa: NN/YYYY 55/2022")
    private String indexNumber;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email mora biti validnog formata")
    private String email;

    @NotBlank(message = "Broj telefona je obavezan")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "Format broja telefona: 061234567")
    private String phone;
}
