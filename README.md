# Mikroserivsna arhitektura - Studentski Upis (Student enrollment)

## Opis projekta
Ovaj projekat predstavlja mikroservisni sistem za urpavljanje studentima i njihovim upisom koristeći Spring Boot i Spring Cloud tehnologije.

## Arhitektura sistema
Sistem je podeljen na četiri glavna modula:

| Modul | Port | Port | Opis |
|--------|------|------|------|
| **discovery-service** | 8761 | /eureka/* | Eureka server – registracija i monitoring servisa |
| **api-gateway** | 8080 | /api/students/* <br> /api/enrollments/* | Centralna tačka ulaza – rutiranje zahteva ka servisima, API-key autentifikacija |
| **student-service** | 8081 | /api/students/* | CRUD nad `Student`, validacija pomoću Hibernate Validatora |
| **orders-service** | 8082 | /api/enrollments/* | CRUD nad `Enrollment` + Feign poziv ka `student-service` + Resilience4j fallback + RabbitMQ događaji |

---