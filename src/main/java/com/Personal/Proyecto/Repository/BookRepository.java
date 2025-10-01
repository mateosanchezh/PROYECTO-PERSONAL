package com.Personal.Proyecto.Repository;

import com.Personal.Proyecto.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
    Optional <Book> findByIsbn (String isbn);
    boolean existsByIsbn (String isbn);

    Optional <Book> findById(Long id);

    boolean existsByIdAndNumeroCopiasGreaterThan(Long bookId, int cantidad);

    //restar copias disponibles.
    @Modifying
    @Query("UPDATE Book b SET b.numeroCopias = b.numeroCopias - 1 WHERE b.id = :bookId AND b.numeroCopias > 0")
    int restarCopia(@Param("bookId") Long bookId);

    // sumar copias disponibles (para devoluciones)
    @Modifying
    @Query("UPDATE Book b SET b.numeroCopias = b.numeroCopias + 1 WHERE b.id = :bookId")
    int sumarCopia(@Param("bookId") Long bookId);
}
