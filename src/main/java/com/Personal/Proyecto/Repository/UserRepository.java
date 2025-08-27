package com.Personal.Proyecto.Repository;

import com.Personal.Proyecto.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findByDocumento(String documento);
    boolean existsByDocumento(String documento);

}
