package com.chad.sales.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chad.sales.exception.ProdutoNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.ItemVenda;
import com.chad.sales.model.Venda;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.ItemVendaRepository;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.repository.VendaRepository;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;

    public ItemVendaService(ItemVendaRepository itemVendaRepository, 
                            VendaRepository vendaRepository,
                            UsuarioRepository usuarioRepository) {
        this.itemVendaRepository = itemVendaRepository;
        this.vendaRepository = vendaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Salvar item garantindo que a venda pertence ao usuário logado
    public ItemVenda salvar(ItemVenda itemVenda) {
        Usuario usuario = getUsuarioLogado();

        Venda venda = vendaRepository.findById(itemVenda.getVenda().getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Venda não encontrada"));

        if (!venda.getUsuario().getId().equals(usuario.getId())) {
            throw new ProdutoNotFoundException("Você não tem permissão para adicionar itens a esta venda");
        }

        itemVenda.setVenda(venda);
        return itemVendaRepository.save(itemVenda);
    }

    // Listar todos os itens de venda do usuário logado
    public List<ItemVenda> listarTodos() {
        Usuario usuario = getUsuarioLogado();
        return itemVendaRepository.findByVendaUsuarioId(usuario.getId());
    }

    // Buscar item por ID garantindo que pertence ao usuário
    public ItemVenda buscarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        return itemVendaRepository.findByIdAndVendaUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Item de venda não encontrado"));
    }

    // Deletar item garantindo permissão
    public void deletar(Long id) {
        ItemVenda item = buscarPorId(id);
        itemVendaRepository.delete(item);
    }

    // Helper para pegar usuário logado
    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) 
                ? ((UserDetails) principal).getUsername() 
                : principal.toString();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário logado não encontrado"));
    }
}