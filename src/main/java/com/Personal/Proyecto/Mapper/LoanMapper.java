package com.Personal.Proyecto.Mapper;

import com.Personal.Proyecto.DTO.LoanDTO;
import com.Personal.Proyecto.DTO.Request.LoanRequestDTO;
import com.Personal.Proyecto.DTO.Response.LoanResponseDTO;
import com.Personal.Proyecto.Model.Book;
import com.Personal.Proyecto.Model.Loan;
import com.Personal.Proyecto.Model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class LoanMapper {

    // Mapeo desde LoanRequestDTO (para crear préstamos)
    public Loan toEntity(LoanRequestDTO dto, User user, Book book, int loanDays) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaDevolucion = fechaInicio.plusDays(loanDays);
        LocalDateTime fechaCreacion = LocalDateTime.now();

        return Loan.builder()
                .user(user)
                .book(book)
                .fechaPrestamo(fechaInicio)
                .fechaDevolucion(fechaDevolucion)
                .fechaCreacion(fechaCreacion)
                .build();
    }

    // Mapeo desde LoanDTO completo (para casos más complejos)
    public Loan toEntity(LoanDTO dto, User user, Book book) {
        return Loan.builder()
                .user(user)
                .book(book)
                .fechaPrestamo(dto.getFechaPrestamo() != null ? dto.getFechaRegreso() : LocalDate.now())
                .fechaDevolucion(dto.getFechaRegreso() != null ? dto.getFechaRegreso() : LocalDate.now().plusDays(15))
                .estado(dto.getEstado() != null ? dto.getEstado() : "Activo")
                .build();
    }

    // Mapeo a LoanDTO
    public LoanDTO toDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setUserId(loan.getUser().getId());
        dto.setBookId(loan.getBook().getId());
        dto.setFechaPrestamo(loan.getFechaPrestamo());
        dto.setFechaRegreso(loan.getFechaDevolucion());
        dto.setEstado(loan.getEstado());
        return dto;
    }

    // Mapeo a ResponseDTO
    public LoanResponseDTO toResponseDTO(Loan loan, String mensaje) {
        return LoanResponseDTO.builder()
                .loanId(loan.getId())
                .userId(loan.getUser().getId())
                .userName(loan.getUser().getNombreCompleto())
                .bookId(loan.getBook().getId())
                .bookTitle(loan.getBook().getTitulo())
                .bookAuthor(loan.getBook().getAutor())
                .bookIsbn(loan.getBook().getIsbn())
                .fechaInicio(loan.getFechaPrestamo())
                .fechaDevolucion(loan.getFechaDevolucion())
                .estado(loan.getEstado())
                .mensaje(mensaje)
                .build();
    }
}
