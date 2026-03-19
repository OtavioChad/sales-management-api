package com.chad.sales.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.chad.sales.model.Venda;

public class VendaResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private List<ItemVendaResponseDTO> itens;
    private double valorTotal;
    private String formaPagamento;

    public VendaResponseDTO(Venda venda) {
        this.id = venda.getId();
        this.cliente = new ClienteResponseDTO(
                venda.getCliente().getId(),
                venda.getCliente().getNome(),
                venda.getCliente().getTelefone()
        );
        this.itens = venda.getItens()
                          .stream()
                          .map(ItemVendaResponseDTO::new)
                          .collect(Collectors.toList());
        this.valorTotal = venda.getValorTotal();
        this.formaPagamento = venda.getFormaPagamento();
    }

    public Long getId() { return id; }
    public ClienteResponseDTO getCliente() { return cliente; }
    public List<ItemVendaResponseDTO> getItens() { return itens; }
    public double getValorTotal() { return valorTotal; }
    public String getFormaPagamento() { return formaPagamento; }
}