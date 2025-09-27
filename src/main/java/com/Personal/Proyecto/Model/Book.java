package com.Personal.Proyecto.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255, nullable = false)
    private String titulo;

    @Column(length = 255, nullable = false)
    private String autor;

    @Column(length = 13, nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private int numeroCopias;

    @Column
    private int anoPublicacion;

    @Column(length = 20,nullable = false)
    public String estado;

    @CreationTimestamp
    public LocalDateTime fechaPublicacion;


}
