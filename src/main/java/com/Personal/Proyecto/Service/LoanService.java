package com.Personal.Proyecto.Service;


import com.Personal.Proyecto.Model.User;
import com.Personal.Proyecto.Repository.BookRepository;
import com.Personal.Proyecto.Repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    //Validar estado del usuario y sus prestamos vencidos
    private void validarUsuarioYLibrosVencidos(User user, Long userId){
        if ("Inactivo".equals(user.getEstado())){
            throw new RuntimeException("El usuario esta inactivo y no puede prestar libros");
        }
        Long prestamosVencidos = loanRepository.contarPrestamosVencidosPorUsuario(userId, LocalDate.now());
        if (prestamosVencidos > 0 ){
            throw new RuntimeException("El usuario con ID: " + userId + " tiene: " + prestamosVencidos + " prestamos vencidos y no puede realizar mas prestamos");
        }
    }

    private void validarDisponibilidadLibro(Long bookId){
        if (!bookRepository.existsByIdAndCopiasDisponiblesGreaterThan(bookId,0)){
            throw new RuntimeException("El libro no tiene copias disponibles");
        }
    }


}
