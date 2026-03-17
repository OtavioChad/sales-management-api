package com.chad.sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.model.Cliente;
import com.chad.sales.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Criar novo cliente
    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    // Listar todos os clientes do usuário logado
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarTodos();
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public Cliente buscarCliente(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    // Deletar cliente
    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
    }
}