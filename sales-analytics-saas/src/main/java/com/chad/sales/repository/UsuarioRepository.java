package com.chad.sales.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chad.sales.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}