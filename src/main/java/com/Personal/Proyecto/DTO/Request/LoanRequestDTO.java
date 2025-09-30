package com.Personal.Proyecto.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private long userId;

    @NotNull(message = "El ID del libro es obligatorio")
    private long bookId;
}
