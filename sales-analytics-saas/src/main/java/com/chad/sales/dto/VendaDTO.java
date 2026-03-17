package com.chad.sales.dto;

import java.util.List;

public class VendaDTO {

    private Long clienteId;
    private String formaPagamento;
    private List<ItemVendaDTO> itens;

    public VendaDTO() {}

    public VendaDTO(Long clienteId, String formaPagamento, List<ItemVendaDTO> itens) {
        this.clienteId = clienteId;
        this.formaPagamento = formaPagamento;
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
}