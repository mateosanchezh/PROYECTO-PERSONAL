package com.Personal.Proyecto.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios",uniqueConstraints = @UniqueConstraint(columnNames = "documento"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;

    @Column(nullable = false,length = 100)
    private String nombreCompleto;

    @Column(length = 15,nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String email;

    @Column(length = 10,nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String direccion;

    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String estado = "Activo";


}
