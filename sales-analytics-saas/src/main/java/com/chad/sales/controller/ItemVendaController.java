package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.dto.ItemVendaRequestDTO;
import com.chad.sales.dto.ItemVendaResponseDTO;
import com.chad.sales.service.ItemVendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    // CRIAR ITEM
    @PostMapping("/venda/{vendaId}")
    public ItemVendaResponseDTO criarItem(
    		@Valid
            @PathVariable Long vendaId,
            @RequestBody ItemVendaRequestDTO dto) {

        return itemVendaService.salvar(vendaId, dto);
    }

    // LISTAR
    @GetMapping
    public List<ItemVendaResponseDTO> listarTodos() {
        return itemVendaService.listarTodos();
    }

    // BUSCAR
    @GetMapping("/{id}")
    public ItemVendaResponseDTO buscarItem(@PathVariable Long id) {
        return itemVendaService.buscarPorId(id);
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Long id) {
        itemVendaService.deletar(id);
    }
}