package com.Personal.Proyecto.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank
    @Size(max = 100)
    private String nombreCompleto;

    @NotBlank
    @Size(min = 5, max = 15)
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{7,10}", message = "El teléfono debe contener solo números (7 a 10 dígitos)")
    private String telefono;

    @NotBlank
    private String direccion;
}
