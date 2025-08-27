package com.Personal.Proyecto.Controller;

import com.Personal.Proyecto.DTO.UserDTO;
import com.Personal.Proyecto.Model.User;
import com.Personal.Proyecto.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> registrarUsuario(@Valid @RequestBody UserDTO request) {
        User usuarioRegistrado = userService.registrarUsuario(request);
        return ResponseEntity.ok(usuarioRegistrado);
    }

}
