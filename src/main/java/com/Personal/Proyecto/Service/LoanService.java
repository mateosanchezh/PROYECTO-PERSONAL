package com.Personal.Proyecto.Service;


import com.Personal.Proyecto.Config.LoanPolicyConfig;
import com.Personal.Proyecto.DTO.Request.LoanRequestDTO;
import com.Personal.Proyecto.DTO.Response.LoanResponseDTO;
import com.Personal.Proyecto.Mapper.LoanMapper;
import com.Personal.Proyecto.Model.Book;
import com.Personal.Proyecto.Model.Loan;
import com.Personal.Proyecto.Model.User;
import com.Personal.Proyecto.Repository.BookRepository;
import com.Personal.Proyecto.Repository.LoanRepository;
import com.Personal.Proyecto.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanPolicyConfig loanPolicy;
    private final UserRepository userRepository;
    private final LoanMapper loanMapper;
    private final EmailService emailService;

    //Validar estado del usuario y sus prestamos vencidos
    private void validarUsuarioYLibrosVencidos(User user, Long userId){
        if ("Inactivo".equals(user.getEstado())){
            throw new RuntimeException("El usuario esta inactivo y no puede prestar libros");
        }
        Long prestamosVencidos = loanRepository.countByUserIdAndEstadoAndFechaDevolucionBefore(userId,"Activo" ,LocalDate.now());
        if (prestamosVencidos > 0 ){
            throw new RuntimeException("El usuario con ID: " + userId + " tiene: " + prestamosVencidos + " prestamos vencidos y no puede realizar mas prestamos");
        }
    }

    //Valida si hay copias de libros disponible.
    private void validarDisponibilidadLibro(Long bookId){
        if (!bookRepository.existsByIdAndNumeroCopiasGreaterThan(bookId,0)){
            throw new RuntimeException("El libro no tiene copias disponibles");
        }
    }

    //Validar limite de prestamos activos
    private void validarPrestamosActivos(Long userId){
        Long prestamosActivos = loanRepository.countByUserIdAndEstado(userId,"Activo");
        if (prestamosActivos > loanPolicy.getMaxPrestamosActivos()){
            throw new RuntimeException("No puede tener mas de: "+ loanPolicy.getMaxPrestamosActivos() + " prestamos activos.");
        };
    }

    //Valida si un usuario ya tiene ese libro prestado
    private void validarPrestamosDuplicados(Long userId,Long bookId){
        if (loanRepository.existsByUserIdAndBookIdAndEstado(userId,bookId,"Activo")){
            throw new RuntimeException("El usuario con ID: " + userId + " ya tiene un préstamo activo para el libro con ID: " + bookId);
        }
    }

    //Actualizar el estado del libro
    private void actualizarEstadoLibro(Book book){
        int actualizarCopias = bookRepository.restarCopia(book.getId());
        if (actualizarCopias == 0){
            throw new RuntimeException(("Error al actualizar las copias disponibles."));
        }

        //Actualiza el estado del libro si ya no hay.
        Book actualizarLibro = bookRepository.findById(book.getId()).orElseThrow();
        if (actualizarLibro.getNumeroCopias() == 0){
            actualizarLibro.setEstado("No disponible");
            bookRepository.save(actualizarLibro);
            log.info("Libro {} marcado como 'No disponible'", actualizarLibro.getTitulo());
        }
    }

    //Metodo para consultar prestamos activos de un usuario
    @Transactional
    public List<LoanResponseDTO> obtenerPrestamosActivos(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("El usuario con ID: " + userId + " no existe.");
        }

        List<Loan> prestamosActivos = loanRepository.findByUserIdAndEstado(userId, "Activo");

        return prestamosActivos.stream()
                .map(loan -> loanMapper.toResponseDTO(loan, "Préstamo Activo"))
                .collect(Collectors.toList());
    }

    //Metodo auxiliar para actualizar prestamos vencidos
    @Transactional
    public int actualizarPrestamosVencidos() {
        List<Loan> overdueLoans = loanRepository.findByEstadoAndFechaDevolucionBefore("Activo",LocalDate.now());

        // Lambda expression para actualizar cada préstamo
        overdueLoans.forEach(loan -> {
            loan.setEstado("Vencido");
            loanRepository.save(loan);
            log.warn("Préstamo {} marcado como vencido", loan.getId());
        });

        log.info("Se actualizaron {} préstamos vencidos", overdueLoans.size());
        return overdueLoans.size();
    }

    @Transactional
    public LoanResponseDTO crearPrestamo (LoanRequestDTO request){

        //Validamos que el usuario exista
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUserId()));

        //Validamos que el libro exista
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new RuntimeException("Libro no encontrado con ID "+ request.getBookId()));

        //Validaciones
        validarUsuarioYLibrosVencidos(user, request.getUserId());

        validateRequestDto(request);

        actualizarEstadoLibro(book);

        Loan loan = loanMapper.toEntity(request, user, book, loanPolicy.getDiasPrestamo());
        Loan guardarPrestamo = loanRepository.save(loan);

        //IMPLEMENTAR NOTIFICACION DE CORREOS

        emailService.enviarCorreoConnfiramcionPrestamo(guardarPrestamo);

        return loanMapper.toResponseDTO(guardarPrestamo, "Prestamo realizado con exito. La fecha de entrega es: "+ guardarPrestamo.getFechaDevolucion());

    }

    private void validateRequestDto(LoanRequestDTO request) {
        validarDisponibilidadLibro(request.getBookId());
        validarPrestamosActivos(request.getUserId());
        validarPrestamosDuplicados(request.getUserId(), request.getBookId());
    }


}
