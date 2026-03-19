# 💼 Sales Management API (Mini SaaS Backend)

API REST desenvolvida com Java e Spring Boot como projeto de estudo, simulando o backend de um sistema SaaS de gestão de vendas, com autenticação JWT, controle de acesso por usuário e aplicação de regras de negócio.

---

## 📌 Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento backend, arquitetura de sistemas e segurança de APIs.

A aplicação simula um ambiente SaaS (Software as a Service), onde cada usuário possui seus próprios dados isolados, como clientes, produtos e vendas.

Foco principal do projeto:

* Estruturação de APIs REST
* Implementação de autenticação e segurança
* Organização em arquitetura em camadas (MVC)
* Aplicação de boas práticas de desenvolvimento backend

---

## 🧠 Principais Funcionalidades

* 🔐 Autenticação stateless com JWT
* 👤 Controle de acesso por usuário (multi-tenant)
* 📦 CRUD de produtos
* 👥 CRUD de clientes
* 💰 Registro e processamento de vendas
* 📊 Regras de negócio (validação de estoque e cálculo automático)
* ⚠️ Tratamento global de exceções
* 📄 Uso de DTOs para transferência de dados

---

## 🛠️ Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* MySQL
* Maven

---

## 🔐 Segurança

* Autenticação baseada em JWT
* Filtro customizado para validação de requisições
* API stateless (sem uso de sessão)
* Criptografia de dados sensíveis

---

## ⚙️ Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/OtavioChad/sales-management-api.git
```

---

### 2. Configurar variáveis de ambiente

Defina as seguintes variáveis no sistema:

```bash
JWT_SECRET=sua_chave_secreta
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

---

### 3. Configurar banco de dados

Crie o banco no MySQL:

```sql
CREATE DATABASE sales_saas;
```

---

### 4. Executar a aplicação

Via Eclipse ou:

```bash
mvn spring-boot:run
```

---

## 📡 Exemplos de Endpoints

| Método | Endpoint       | Descrição           |
| ------ | -------------- | ------------------- |
| POST   | /auth/login    | Autenticação        |
| POST   | /auth/register | Cadastro de usuário |
| GET    | /produtos      | Listar produtos     |
| POST   | /produtos      | Criar produto       |

---

## 📈 Status do Projeto

🚧 Em desenvolvimento

Este projeto faz parte do meu processo de aprendizado em desenvolvimento backend, sendo continuamente evoluído com novas funcionalidades, melhorias de segurança e boas práticas.

---

## 👨‍💻 Autor

Otávio Chad
GitHub: https://github.com/OtavioChad
LinkedIn: https://www.linkedin.com/in/otáviochad/

---

## 🎯 Objetivo

Projeto desenvolvido para fins de estudo e prática, com foco na construção de APIs seguras e bem estruturadas utilizando Java e Spring Boot, visando aplicação em ambientes reais de desenvolvimento.
