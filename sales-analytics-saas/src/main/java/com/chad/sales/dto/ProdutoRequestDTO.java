package com.chad.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProdutoRequestDTO {

	@NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Preço de custo é obrigatório")
    @Positive(message = "Preço de custo deve ser maior que zero")
    private Double precoCusto;

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser maior que zero")
    private Double precoVenda;

    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque;

    
    
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Double getPrecoCusto() {
		return precoCusto;
	}
	public void setPrecoCusto(Double precoCusto) {
		this.precoCusto = precoCusto;
	}
	public Double getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

}