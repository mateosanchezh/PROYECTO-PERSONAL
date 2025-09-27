package com.Personal.Proyecto.DTO.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookResponseDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private Integer copiasDisponibles;
    private Integer anoPublicacion;
    private String estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
    private String mensaje;
}
