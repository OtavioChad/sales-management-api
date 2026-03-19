package com.chad.sales.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chad.sales.dto.ClienteRequestDTO;
import com.chad.sales.dto.ClienteResponseDTO;
import com.chad.sales.exception.ClienteNotFoundException;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Cliente;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.ClienteRepository;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.config.AuthUtil;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthUtil authUtil;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public ClienteService(ClienteRepository clienteRepository,
                          UsuarioRepository usuarioRepository,
                          AuthUtil authUtil,
                          UsuarioAutenticadoService usuarioAutenticadoService) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.authUtil = authUtil;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {

    	Usuario usuario = usuarioAutenticadoService.get();

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setUsuario(usuario);

        Cliente salvo = clienteRepository.save(cliente);

        return toDTO(salvo);
    }

    public List<ClienteResponseDTO> listarTodos() {

    	Usuario usuario = usuarioAutenticadoService.get();

        return clienteRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = buscarPorIdEntity(id);
        return toDTO(cliente);
    }

    public Cliente buscarPorIdEntity(Long id) {

    	Usuario usuario = usuarioAutenticadoService.get();

        return clienteRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() ->
                    new ClienteNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        Cliente cliente = buscarPorIdEntity(id);
        clienteRepository.delete(cliente);
    }

    private ClienteResponseDTO toDTO(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getTelefone()
        );
    }
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {

        Usuario usuario = usuarioAutenticadoService.get();

        Cliente cliente = clienteRepository
                .findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));

        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());

        clienteRepository.save(cliente);

        return new ClienteResponseDTO(cliente);
    }
}