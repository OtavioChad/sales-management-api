package com.chad.sales.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chad.sales.config.AuthUtil;
import com.chad.sales.dto.VendaRequestDTO;
import com.chad.sales.exception.ClienteNotFoundException;
import com.chad.sales.exception.EstoqueInsuficienteException;
import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Cliente;
import com.chad.sales.model.ItemVenda;
import com.chad.sales.model.Produto;
import com.chad.sales.model.Usuario;
import com.chad.sales.model.Venda;
import com.chad.sales.repository.ClienteRepository;
import com.chad.sales.repository.ProdutoRepository;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
  
    public VendaService(VendaRepository vendaRepository,
                        UsuarioRepository usuarioRepository,
                        ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository,
                        AuthUtil authUtil,
                        UsuarioAutenticadoService usuarioAutenticadoService) {

        this.vendaRepository = vendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        
    }

    @Transactional
    public Venda salvar(VendaRequestDTO dto) {

    	Usuario usuario = usuarioAutenticadoService.get();
        Cliente cliente = clienteRepository
                .findByIdAndUsuarioId(dto.getClienteId(), usuario.getId())
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente não encontrado"));

        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setCliente(cliente);
        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setData(LocalDateTime.now());

        List<ItemVenda> itens = dto.getItens().stream().map(itemDTO -> {

            Produto produto = produtoRepository
                    .findByIdAndUsuarioId(itemDTO.getProdutoId(), usuario.getId())
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para: " + produto.getNome());
            }

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPreco(produto.getPrecoVenda());
            item.setVenda(venda);

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());

            return item;
        }).toList();

        venda.setItens(itens);

        double total = itens.stream()
                .mapToDouble(i -> i.getPreco() * i.getQuantidade())
                .sum();

        venda.setValorTotal(total);

        return vendaRepository.save(venda);
    }
    
    public Venda buscarPorId(Long id) {
    	Usuario usuario = usuarioAutenticadoService.get();
        return vendaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }
    
    public void deletar(Long id) {
        Venda venda = buscarPorId(id);
        vendaRepository.delete(venda);
    }
    
    public List<Venda> listarTodas() {
    	Usuario usuario = usuarioAutenticadoService.get();
        return vendaRepository.findByUsuarioId(usuario.getId());
    }
}