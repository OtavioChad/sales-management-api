package com.chad.sales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemVendaRequestDTO {
	
	@NotNull(message = "Produto é obrigatório")
    private Long produtoId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    private Integer quantidade;
    
    
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}