package com.chad.sales.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.chad.sales.config.AuthUtil;
import com.chad.sales.dto.ProdutoRequestDTO;
import com.chad.sales.dto.ProdutoResponseDTO;
import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Produto;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.ProdutoRepository;
import com.chad.sales.repository.UsuarioRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    public ProdutoService(ProdutoRepository produtoRepository, 
            UsuarioRepository usuarioRepository,
            AuthUtil authUtil,
            UsuarioAutenticadoService usuarioAutenticadoService) {
	this.produtoRepository = produtoRepository;
	this.usuarioAutenticadoService = usuarioAutenticadoService;
}
    
    private Produto buscarPorIdEntity(Long id) {
    	Usuario usuario = usuarioAutenticadoService.get();
        return produtoRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
    }

    // Listar todos os produtos do usuário logado
    public List<ProdutoResponseDTO> listarTodos() {
    	Usuario usuario = usuarioAutenticadoService.get();
        return produtoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar produto por ID garantindo que pertence ao usuário
    public ProdutoResponseDTO buscarPorId(Long id) {
    	Usuario usuario = usuarioAutenticadoService.get();
        Produto produto = produtoRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
        return toDTO(produto);
    }

    // Deletar produto garantindo permissão
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
    	Usuario usuario = usuarioAutenticadoService.get();
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setPrecoCusto(dto.getPrecoCusto());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setEstoque(dto.getEstoque());
        produto.setUsuario(usuario);
        Produto salvo = produtoRepository.save(produto);

        return toDTO(salvo);
    }
    
    private ProdutoResponseDTO toDTO(Produto produto) {
        return new ProdutoResponseDTO(
            produto.getId(),
            produto.getNome(),
            produto.getCategoria(),
            produto.getPrecoVenda(),
            produto.getEstoque()
        );
    }
    
    public void deletar(Long id) {
        Produto produto = buscarPorIdEntity(id);
        produtoRepository.delete(produto);
    }
    
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto existente = buscarPorIdEntity(id);
        existente.setNome(dto.getNome());
        existente.setCategoria(dto.getCategoria());
        existente.setPrecoCusto(dto.getPrecoCusto());
        existente.setPrecoVenda(dto.getPrecoVenda());
        existente.setEstoque(dto.getEstoque());
        Produto salvo = produtoRepository.save(existente);
        return toDTO(salvo);
    }
}