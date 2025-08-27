package com.Personal.Proyecto.Service;

import com.Personal.Proyecto.DTO.UserDTO;
import com.Personal.Proyecto.Model.User;
import com.Personal.Proyecto.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User registrarUsuario(UserDTO request){
        if (userRepository.existsByDocumento(request.getDocumento())){
            throw new RuntimeException("Ya existe un usuario con este documento");
        }
        User user = User.builder()
                .nombreCompleto(request.getNombreCompleto())
                .documento(request.getDocumento())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .estado("Activo")
                .build();
        return userRepository.save(user);
    }
}
