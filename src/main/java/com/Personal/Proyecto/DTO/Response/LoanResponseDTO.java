package com.Personal.Proyecto.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class LoanResponseDTO {
    private long loanId;
    private long userId;
    private String username;
    private long bookid;
    private String bookTitulo;
    private String bookAutor;
    private String bookCategoria;
    private String isbn;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private LocalDate fechaDevolucion;
    private String estado;
    private String mensaje;
}
