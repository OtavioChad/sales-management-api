package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.dto.ClienteRequestDTO;
import com.chad.sales.dto.ClienteResponseDTO;
import com.chad.sales.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Criar cliente
    @PostMapping
    public ClienteResponseDTO criarCliente(@RequestBody @Valid ClienteRequestDTO dto) {
        return clienteService.salvar(dto);
    }

    // Listar clientes
    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ClienteResponseDTO buscarCliente(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
    }
    
    @PutMapping("/{id}")
    public ClienteResponseDTO atualizarCliente(
    		@Valid
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO dto) {

        return clienteService.atualizar(id, dto);
    }
}