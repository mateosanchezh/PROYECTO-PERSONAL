package com.Personal.Proyecto.Service;

import com.Personal.Proyecto.DTO.BookDTO;
import com.Personal.Proyecto.DTO.Response.BookResponseDTO;
import com.Personal.Proyecto.Model.Book;
import com.Personal.Proyecto.Mapper.BookMapper;
import com.Personal.Proyecto.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookResponseDTO registrarBook(BookDTO request) {

        // Validacion del ISBN
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException(" Ya existe un libro con ese ISBN");
        }

        //Validacion el año de publicacion no puede venir vacio
        if (request.getAnoPublicacion() != null ){
            //Validacion de fecha publicacion no debe ser mayor al año actual.
            int anoActual = LocalDateTime.now().getYear();
            if (request.getAnoPublicacion() > anoActual){
                throw new RuntimeException("El año de publicación no puede ser mayor al año actual (" + anoActual + ")");
            }
        }

        Book book = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponseDTO(savedBook);

    }

}
