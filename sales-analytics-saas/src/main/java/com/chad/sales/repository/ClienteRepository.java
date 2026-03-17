package com.chad.sales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chad.sales.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByUsuarioId(Long usuarioId);

    Optional<Cliente> findByIdAndUsuarioId(Long id, Long usuarioId);
}