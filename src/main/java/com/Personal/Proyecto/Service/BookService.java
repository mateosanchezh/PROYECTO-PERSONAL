package com.Personal.Proyecto.Service;

import com.Personal.Proyecto.DTO.BookDTO;
import com.Personal.Proyecto.Model.Book;
import com.Personal.Proyecto.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Book registrarBook(BookDTO request) {
        if (bookRepository.existsByIsbn(request.getIsbn())){
            throw  new RuntimeException("Ya existe un libro con ese ISBN");
        }
        Book book = Book.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .isbn(request.getIsbn())
                .categoria(request.getCategoria())
                .anoPublicacion(request.getAnoPublicacion())
                .numeroCopias(request.getNumeroCopias())
                .estado("Disponible") //Estado inicial.
                .build();

        return bookRepository.save(book);

    }

}
