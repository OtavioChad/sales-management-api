package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.model.Produto;
import com.chad.sales.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Criar produto
    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    // Listar todos os produtos
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarTodos();
    }

    // Buscar por id
    @GetMapping("/{id}")
    public Produto buscarProduto(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto existente = produtoService.buscarPorId(id);
        existente.setNome(produto.getNome());
        existente.setCategoria(produto.getCategoria());
        existente.setPrecoCusto(produto.getPrecoCusto());
        existente.setPrecoVenda(produto.getPrecoVenda());
        existente.setEstoque(produto.getEstoque());
        return produtoService.salvar(existente);
    }

    // Deletar produto
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}