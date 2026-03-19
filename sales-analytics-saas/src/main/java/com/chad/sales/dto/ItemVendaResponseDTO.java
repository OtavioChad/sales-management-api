package com.chad.sales.dto;

import com.chad.sales.model.ItemVenda;

public class ItemVendaResponseDTO {
    private Long id;
    private String nomeProduto;
    private double preco;
    private int quantidade;

    public ItemVendaResponseDTO(ItemVenda item) {
        this.id = item.getId();
        this.nomeProduto = item.getProduto().getNome();
        this.preco = item.getPreco();
        this.quantidade = item.getQuantidade();
    }

	public Long getId() {
		return id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public double getPreco() {
		return preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

    
}