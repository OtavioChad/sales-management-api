package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.dto.ProdutoRequestDTO;
import com.chad.sales.dto.ProdutoResponseDTO;
import com.chad.sales.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Criar produto
    @PostMapping
    public ProdutoResponseDTO criarProduto(@RequestBody @Valid ProdutoRequestDTO dto) {
        return produtoService.salvar(dto);
    }

    // Listar todos os produtos
    @GetMapping
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoService.listarTodos();
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarProduto(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizarProduto(@PathVariable Long id,
                                               @RequestBody @Valid ProdutoRequestDTO dto) {
        return produtoService.atualizar(id, dto);
    }
    

    // Deletar produto
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}