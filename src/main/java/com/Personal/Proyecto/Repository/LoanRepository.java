package com.Personal.Proyecto.Repository;

import com.Personal.Proyecto.Model.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository <Loan, Long > {

    // Contar préstamos activos de un usuario
    Long countByUserIdAndEstado(Long usuarioId, String estado);

    // Contar préstamos vencidos de un usuario
    Long countByUserIdAndEstadoAndFechaDevolucionBefore(Long usuarioId, String estado, LocalDate fechaActual);

    // Obtener préstamos activos de un usuario (carga user y book para evitar lazy)
    @EntityGraph(attributePaths = {"user", "book"})
    List<Loan> findByUserIdAndEstado(Long usuarioId, String estado);

    // Obtener todos los préstamos vencidos del sistema (carga user y book)
    @EntityGraph(attributePaths = {"user", "book"})
    List<Loan> findByEstadoAndFechaDevolucionBefore(String estado, LocalDate fechaActual);

    // Verificar si existe un préstamo activo específico usuario-libro
    boolean existsByUserIdAndBookIdAndEstado(Long usuarioId, Long libroId, String estado);

}

