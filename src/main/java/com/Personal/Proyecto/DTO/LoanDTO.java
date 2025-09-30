package com.Personal.Proyecto.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El ID del libro es obligatorio")
    private Long bookId;

    private LocalDate fechaPrestamo;
    private LocalDate fechaRegreso;
    private String estado;
}
