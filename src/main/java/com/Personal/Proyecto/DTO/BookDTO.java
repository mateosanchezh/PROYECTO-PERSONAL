package com.Personal.Proyecto.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank(message = "El titulo es requerido")
    @Size(min=3, message = "El titulo debe tener por lo menos 3 caracteres")
    private String titulo;

    @NotBlank(message = "El Autor es requerido")
    private String autor;

    @NotBlank(message = "El ISBN no debe estar vacio")
    @Size(min = 13,max = 13,message = "El codigo ISBN debe tener 13 caracteres")
    private String isbn;

    @NotBlank(message = "La categoria no debe estar vacia")
    private String categoria;

    @NotNull(message = "El número de copias es requerido")
    @Min(value = 1, message = "Debe haber al menos 1 copia disponible")
    private int numeroCopias;

    @Min(value = 1500, message = "El año debe ser mayor o igual a 1500")
    private Integer anoPublicacion;

}
