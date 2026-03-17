package com.chad.sales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chad.sales.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Lista todos os produtos de um usuário específico
    List<Produto> findByUsuarioId(Long usuarioId);

    // Busca um produto pelo id e usuário, retorna Optional para usar orElseThrow
    Optional<Produto> findByIdAndUsuarioId(Long id, Long usuarioId);
}