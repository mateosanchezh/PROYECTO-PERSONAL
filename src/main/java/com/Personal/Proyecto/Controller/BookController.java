package com.Personal.Proyecto.Controller;

import com.Personal.Proyecto.DTO.BookDTO;
import com.Personal.Proyecto.Model.Book;
import com.Personal.Proyecto.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity <Book> registrarLibro(@Valid @RequestBody BookDTO request){
        Book bookRegistrado = bookService.registrarBook(request);
        return ResponseEntity.ok(bookRegistrado);
    }
}
