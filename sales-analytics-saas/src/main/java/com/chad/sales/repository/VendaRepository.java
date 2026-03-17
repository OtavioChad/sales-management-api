package com.chad.sales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chad.sales.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Lista todas as vendas de um usuário específico
    List<Venda> findByUsuarioId(Long usuarioId);

    // Busca uma venda específica do usuário
    Optional<Venda> findByIdAndUsuarioId(Long id, Long usuarioId);

}