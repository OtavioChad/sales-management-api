package com.chad.sales.dto;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private Double precoVenda;
    private Integer estoque;

    public ProdutoResponseDTO(Long id, String nome, String categoria, Double precoVenda, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public Double getPrecoVenda() { return precoVenda; }
    public Integer getEstoque() { return estoque; }

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}