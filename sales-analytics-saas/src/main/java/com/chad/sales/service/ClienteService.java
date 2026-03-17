package com.chad.sales.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chad.sales.exception.ClienteNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Cliente;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.ClienteRepository;
import com.chad.sales.repository.UsuarioRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Salvar cliente atribuindo ao usuário logado
    public Cliente salvar(Cliente cliente) {
        Usuario usuario = getUsuarioLogado();
        cliente.setUsuario(usuario);
        return clienteRepository.save(cliente);
    }

    // Listar todos os clientes do usuário logado
    public List<Cliente> listarTodos() {
        Usuario usuario = getUsuarioLogado();
        return clienteRepository.findByUsuarioId(usuario.getId());
    }

    // Buscar cliente por ID garantindo que pertence ao usuário logado
    public Cliente buscarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        return clienteRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    // Deletar cliente garantindo permissão
    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

    // Helper para pegar o usuário logado
    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário logado não encontrado"));
    }
}