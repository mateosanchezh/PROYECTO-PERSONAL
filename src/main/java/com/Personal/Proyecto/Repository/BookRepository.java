package com.Personal.Proyecto.Repository;

import com.Personal.Proyecto.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
    Optional <Book> findByIsbn (String isbn);
    boolean existsByIsbn (String isbn);

    boolean existsByIdAndCopiasDisponiblesGreaterThan(Long bookId, int cantidad);

}
