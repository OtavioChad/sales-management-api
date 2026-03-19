package com.chad.sales.dto;

import com.chad.sales.model.Cliente;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String telefone;

    // Construtor padrão com campos
    public ClienteResponseDTO(Long id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Novo construtor que recebe a entidade Cliente
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}