package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.model.ItemVenda;
import com.chad.sales.service.ItemVendaService;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    // Criar um novo item de venda
    @PostMapping
    public ItemVenda criarItem(@RequestBody ItemVenda itemVenda) {
        return itemVendaService.salvar(itemVenda);
    }

    // Listar todos os itens de venda
    @GetMapping
    public List<ItemVenda> listarItens() {
        return itemVendaService.listarTodos();
    }

    // Buscar um item específico
    @GetMapping("/{id}")
    public ItemVenda buscarItem(@PathVariable Long id) {
        return itemVendaService.buscarPorId(id);
    }

    // Deletar um item
    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Long id) {
        itemVendaService.deletar(id);
    }
}