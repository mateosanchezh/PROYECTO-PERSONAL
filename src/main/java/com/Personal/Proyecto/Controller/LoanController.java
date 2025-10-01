package com.Personal.Proyecto.Controller;

import com.Personal.Proyecto.DTO.Request.LoanRequestDTO;
import com.Personal.Proyecto.DTO.Response.LoanResponseDTO;
import com.Personal.Proyecto.Service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/crear")
    public ResponseEntity <LoanResponseDTO> crearPrestamo(@Valid @RequestBody LoanRequestDTO request){
        try {
            LoanResponseDTO response = loanService.crearPrestamo(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            LoanResponseDTO errorResponse = LoanResponseDTO.builder()
                    .mensaje("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}/activos")
    public ResponseEntity <List<LoanResponseDTO>> obtenerPrestamosActivos(@PathVariable Long userId){
        try {
            List<LoanResponseDTO> loans = loanService.obtenerPrestamosActivos(userId);
            return ResponseEntity.ok(loans);
            } catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/actualizar-vencidos")
    public ResponseEntity<String> actualizarPrestamosVencidos() {
        try {
            int updatedLoans = loanService.actualizarPrestamosVencidos();
            return ResponseEntity.ok(updatedLoans + " préstamos actualizados a estado vencido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar préstamos vencidos: " + e.getMessage());
        }
    }

}
