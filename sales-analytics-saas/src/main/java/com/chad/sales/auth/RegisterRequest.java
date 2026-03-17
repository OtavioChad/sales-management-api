package com.chad.sales.auth;

//REPRESENTA A CRIAÇÃO DO USUARIO
//{
//	 "nome": "Otavio",
//	 "email": "otavio@email.com",
//	 "senha": "123"
//	}

public class RegisterRequest {

    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}