package com.chad.sales.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class VendaRequestDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private String formaPagamento;

    @NotEmpty(message = "A venda deve ter pelo menos um item")
    @Valid List<ItemVendaRequestDTO> itens;

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

    public List<ItemVendaRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDTO> itens) {
        this.itens = itens;
    }
}