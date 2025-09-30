package com.Personal.Proyecto.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LoanPolicyConfig{

    @Value("${biblioteca.prestamo.max-prestamos-activos:5}")
    private int maxPrestamosActivos;

    @Value("${biblioteca.prestamo.dias-prestamo:15}")
    private int diasPrestamo;

    @Value("${biblioteca.prestamo.estudiante-max-prestamos:3}")
    private int estudianteMaxPrestamos;

    @Value("${biblioteca.prestamo.docente-max-prestamos:10}")
    private int docenteMaxPrestamos;

}
