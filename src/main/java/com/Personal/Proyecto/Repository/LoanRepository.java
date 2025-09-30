package com.Personal.Proyecto.Repository;

import com.Personal.Proyecto.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository <Loan, Long > {

    // Contar préstamos activos de un usuario
    @Query("SELECT COUNT(p) FROM Loan p WHERE p.user.id = :usuarioId AND p.estado = 'Activo'")
    Long contarPrestamosActivosPorUsuario(@Param("usuarioId") Long usuarioId);

    // Verificar si un usuario tiene préstamos vencidos
    @Query("SELECT COUNT(p) FROM Loan p WHERE p.user.id = :usuarioId AND p.estado = 'Activo' AND p.fechaDevolucion < :fechaActual")
    Long contarPrestamosVencidosPorUsuario(@Param("usuarioId") Long usuarioId, @Param("fechaActual") LocalDate fechaActual);

    // Obtener préstamos activos de un usuario
    @Query("SELECT p FROM Loan p JOIN FETCH p.user JOIN FETCH p.book WHERE p.user.id = :usuarioId AND p.estado = 'Activo'")
    List<Loan> obtenerPrestamosActivosPorUsuario(@Param("usuarioId") Long usuarioId);

    // Obtener todos los préstamos vencidos del sistema
    @Query("SELECT p FROM Loan p JOIN FETCH p.user JOIN FETCH p.book WHERE p.estado = 'Activo' AND p.fechaDevolucion < :fechaActual")
    List<Loan> obtenerPrestamosVencidos(@Param("fechaActual") LocalDate fechaActual);

    // Verificar si existe un préstamo activo específico usuario-libro
    @Query("SELECT COUNT(p) > 0 FROM Loan p WHERE p.user.id = :usuarioId AND p.book.id = :libroId AND p.estado = 'Activo'")
    boolean existePrestamoActivoPorUsuarioYLibro(@Param("usuarioId") Long usuarioId, @Param("libroId") Long libroId);

}

