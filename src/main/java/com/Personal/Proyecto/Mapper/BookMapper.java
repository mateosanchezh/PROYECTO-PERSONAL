package com.Personal.Proyecto.Mapper;

import com.Personal.Proyecto.DTO.BookDTO;
import com.Personal.Proyecto.DTO.Response.BookResponseDTO;
import com.Personal.Proyecto.Model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookDTO dto) {
        return Book.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .isbn(dto.getIsbn())
                .categoria(dto.getCategoria())
                .anoPublicacion(dto.getAnoPublicacion())
                .numeroCopias(dto.getNumeroCopias())
                //.estado("Disponible") ESTADO INICIAL CONTROLADO DESDE EL MODELO.
                .build();
    }

    public BookResponseDTO toResponseDTO(Book book, String mensaje){
        return BookResponseDTO.builder()
                .id(book.getId())
                .titulo(book.getTitulo())
                .autor(book.getAutor())
                .isbn(book.getIsbn())
                .categoria(book.getCategoria())
                .anoPublicacion(book.getAnoPublicacion())
                .estado(book.getEstado())
                .copiasDisponibles(book.getNumeroCopias())
                .fechaRegistro(book.getFechaRegistro())
                .mensaje(mensaje)
                .build();
    }

    public BookResponseDTO toResponseDTO(Book book){
        return toResponseDTO(book,"Libro registrado exitosamente");
    }
}
