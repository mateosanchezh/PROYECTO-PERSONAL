package com.Personal.Proyecto.DTO.Response;

import java.time.LocalDateTime;

public class LoanResponseDTO {
    private long loanId;
    private long userId;
    private String username;
    private long bookid;
    private String bookTitulo;
    private String bookAutor;
    private String bookCategoria;
    private String isbn;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private String estado;
    private String mesnsaje;
}
