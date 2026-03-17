package com.chad.sales.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Produto;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.ProdutoRepository;
import com.chad.sales.repository.UsuarioRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Salvar produto do usuário logado
    public Produto salvar(Produto produto) {
        Usuario usuario = getUsuarioLogado();
        produto.setUsuario(usuario);
        return produtoRepository.save(produto);
    }

    // Listar todos os produtos do usuário logado
    public List<Produto> listarTodos() {
        Usuario usuario = getUsuarioLogado();
        return produtoRepository.findByUsuarioId(usuario.getId());
    }

    // Buscar produto por ID garantindo que pertence ao usuário
    public Produto buscarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        return produtoRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto com ID " + id + " não encontrado"));
    }

    // Deletar produto garantindo permissão
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    // Helper para pegar o usuário logado
    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário logado não encontrado"));
    }
}