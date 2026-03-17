package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.dto.VendaDTO;
import com.chad.sales.model.Venda;
import com.chad.sales.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // Criar uma nova venda usando DTO
    @PostMapping
    public Venda criarVenda(@RequestBody VendaDTO vendaDTO) {
        return vendaService.salvarComDTO(vendaDTO);
    }

    // Listar todas as vendas do usuário logado
    @GetMapping
    public List<Venda> listarVendas() {
        return vendaService.listarTodosDoUsuario();
    }

    // Buscar uma venda específica do usuário logado
    @GetMapping("/{id}")
    public Venda buscarVenda(@PathVariable Long id) {
        return vendaService.buscarPorIdDoUsuario(id);
    }

    // Deletar uma venda do usuário logado
    @DeleteMapping("/{id}")
    public void deletarVenda(@PathVariable Long id) {
        vendaService.deletar(id);
    }
}