package com.Personal.Proyecto.Service;

import com.Personal.Proyecto.Model.Loan;
import com.Personal.Proyecto.Model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void enviarCorreoConnfiramcionPrestamo(Loan loan){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            User user = loan.getUser();
            helper.setTo(user.getEmail());
            helper.setSubject("Confirmacion de prestamo — Biblioteca");


            Context contenido = new Context();
            contenido.setVariable("nombreUsuario", user.getNombreCompleto());
            contenido.setVariable("tituloLibro", loan.getBook().getTitulo());
            contenido.setVariable("autorLibro", loan.getBook().getAutor());
            contenido.setVariable("fechaPrestamo", loan.getFechaPrestamo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            contenido.setVariable("fechaDevolucion", loan.getFechaDevolucion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            String htmlContenet = templateEngine.process("email/confirmacion-prestamo",contenido);
            helper.setText(htmlContenet, true);

            mailSender.send(message);
            log.info("Correo de confirmación enviado a: {}", user.getEmail());
        } catch (MessagingException e){
            log.error("Error al enviar correo de confirmación a: {}", loan.getUser().getEmail(), e);

        }
    }
}
