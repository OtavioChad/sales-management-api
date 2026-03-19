package com.chad.sales.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chad.sales.config.AuthUtil;
import com.chad.sales.dto.ItemVendaRequestDTO;
import com.chad.sales.dto.ItemVendaResponseDTO;
import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.ItemVenda;
import com.chad.sales.model.Produto;
import com.chad.sales.model.Usuario;
import com.chad.sales.model.Venda;
import com.chad.sales.repository.ItemVendaRepository;
import com.chad.sales.repository.ProdutoRepository;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.repository.VendaRepository;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final AuthUtil authUtil;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public ItemVendaService(ItemVendaRepository itemVendaRepository,
                            VendaRepository vendaRepository,
                            UsuarioRepository usuarioRepository,
                            ProdutoRepository produtoRepository,
                            AuthUtil authUtil,
                            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.itemVendaRepository = itemVendaRepository;
        this.vendaRepository = vendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.authUtil = authUtil;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    // SALVAR COM DTO
    public ItemVendaResponseDTO salvar(Long vendaId, ItemVendaRequestDTO dto) {

    	Usuario usuario = usuarioAutenticadoService.get();

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new ProdutoNotFoundException("Venda não encontrada"));

        if (!venda.getUsuario().getId().equals(usuario.getId())) {
            throw new ProdutoNotFoundException("Sem permissão para essa venda");
        }
        
        Produto produto = produtoRepository
                .findByIdAndUsuarioId(dto.getProdutoId(), usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        ItemVenda item = new ItemVenda();
        item.setVenda(venda);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setPreco(produto.getPrecoVenda());

        ItemVenda salvo = itemVendaRepository.save(item);

        return new ItemVendaResponseDTO(salvo);
    }

    // LISTAR
    public List<ItemVendaResponseDTO> listarTodos() {
    	Usuario usuario = usuarioAutenticadoService.get();

        return itemVendaRepository.findByVendaUsuarioId(usuario.getId())
                .stream()
                .map(ItemVendaResponseDTO::new)
                .toList();
    }

    // BUSCAR POR ID
    public ItemVendaResponseDTO buscarPorId(Long id) {
    	Usuario usuario = usuarioAutenticadoService.get();

        ItemVenda item = itemVendaRepository
                .findByIdAndVendaUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Item não encontrado"));

        return new ItemVendaResponseDTO(item);
    }

    // DELETAR
    public void deletar(Long id) {
    	Usuario usuario = usuarioAutenticadoService.get();
        ItemVenda item = itemVendaRepository
                .findByIdAndVendaUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Item não encontrado"));

        itemVendaRepository.delete(item);
    }
}