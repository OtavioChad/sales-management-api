package com.chad.sales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chad.sales.model.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
	List<ItemVenda> findByVendaUsuarioId(Long usuarioId);
	Optional<ItemVenda> findByIdAndVendaUsuarioId(Long id, Long usuarioId);
}