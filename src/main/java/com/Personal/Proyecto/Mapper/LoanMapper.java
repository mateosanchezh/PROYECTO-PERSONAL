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

    public LoanResponseDTO toResponseDTO(Loan loan, String mensaje) {
        return LoanResponseDTO.builder()
                .loanId(loan.getId())
                .userId(loan.getUser().getId())
                .username(loan.getUser().getNombreCompleto())
                .bookid(loan.getBook().getId())
                .bookTitulo(loan.getBook().getTitulo())
                .bookCategoria(loan.getBook().getCategoria())
                .bookAutor(loan.getBook().getAutor())
                .isbn(loan.getBook().getIsbn())
                .fechaInicio(loan.getFechaPrestamo())
                .fechaDevolucion(loan.getFechaDevolucion())
                .estado(loan.getEstado())
                .mensaje(mensaje)
                .build();
    }

    public LoanResponseDTO toResponseDTO(Loan loan) {
        return toResponseDTO(loan, "Préstamo realizado exitosamente");
    }
}
