package com.chad.sales.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.chad.sales.dto.VendaRequestDTO;
import com.chad.sales.dto.VendaResponseDTO;
import com.chad.sales.model.Venda;
import com.chad.sales.service.VendaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // CRIAR
    @PostMapping
    public VendaResponseDTO salvar(@Valid @RequestBody VendaRequestDTO dto) {
        return new VendaResponseDTO(vendaService.salvar(dto));
    }

    // LISTAR
    @GetMapping
    public List<VendaResponseDTO> listar() {
        return vendaService.listarTodas()
                .stream()
                .map(VendaResponseDTO::new)
                .toList();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public VendaResponseDTO buscar(@PathVariable Long id) {
        return new VendaResponseDTO(vendaService.buscarPorId(id));
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        vendaService.deletar(id);
    }
}