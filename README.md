#  Spring Boot E-commerce API

API REST desenvolvida com **Java 21 + Spring Boot 3.2.5**, seguindo boas práticas de arquitetura, segurança e desenvolvimento backend profissional.

---

##  Visão Geral

Este projeto simula um sistema completo de **e-commerce**, com gerenciamento de:

- usuários
- produtos
- categorias
- pedidos
- itens de pedido
- pagamentos
- autenticação

A aplicação foi construída com foco em:

- arquitetura em camadas
- separação de responsabilidades
- uso de DTOs
- validação de dados
- tratamento global de exceções
- segurança com Spring Security

---

##  Arquitetura

```text
Controller → Service → Repository → Database
               ↓
             DTO / Mapper



             com.projetospringboot.projeto
│
├── config       → dados iniciais (seed)
├── controller   → endpoints REST
├── dto          → entrada/saída da API
├── entity       → entidades JPA
├── exception    → tratamento global de erros
├── mapper       → conversão Entity ↔ DTO
├── repository   → acesso ao banco
├── security     → configuração de segurança
├── services     → regras de negócio


## Tecnologias
Java 21
Spring Boot 3.2.5
Spring Web
Spring Data JPA
Spring Security
Hibernate
H2 Database
Maven
Jakarta Validation

## Segurança

A aplicação utiliza Spring Security com configuração básica.

## Rotas públicas
/auth/**
/h2-console/**
GET /products/**
GET /categories/**
GET /orders/**
GET /users/**

## Rotas protegidas
POST, PUT, DELETE → requer autenticação


## Funcionalidades

##  Usuários
CRUD completo
validação de dados

## Produtos
CRUD completo
validação de preço

## Categorias
CRUD completo
bloqueio de duplicidade (case insensitive)

##  Pedidos
criação de pedidos
cálculo automático de valores

##  Itens do pedido
chave composta (@EmbeddedId)
desconto por quantidade

##  Pagamentos
relação 1:1 com pedido (@MapsId)
## Regras de Negócio

## Pedido
cálculo de subtotal
desconto geral:
pedidos acima de R$100 → 10%
cálculo do total final

## Item do pedido
desconto por quantidade:
≥ 5 unidades → 5%
≥ 10 unidades → 10%

## Endpoints

## Usuários
GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}

## Produtos
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}

## Categorias
GET    /categories
GET    /categories/{id}
POST   /categories
PUT    /categories/{id}
DELETE /categories/{id}

## Pedidos
GET    /orders
GET    /orders/{id}
POST   /orders
PUT    /orders/{id}
DELETE /orders/{id}

## Autenticação
POST /auth/login

##  Exemplos de Resposta
{
  "id": 1,
  "clientId": 1,
  "clientName": "Maria Brown",
  "moment": "2019-06-20T19:53:07Z",
  "status": "WAITING_PAYMENT",
  "total": 1287.9,
  "subtotal": 1431,
  "desconto": 143.1,
  "items": [
    {
      "productName": "The Lord of the Rings",
      "quantity": 2,
      "price": 90.5,
      "subTotal": 181,
      "discount": 0
    }
  ]
}

##  Tratamento de Erros

{
  "timestamp": "2026-03-23T19:00:00Z",
  "status": 400,
  "path": "/users",
  "error": "Erro de validação",
  "message": "Campos inválidos"
}


##  Exemplo de resposta
{
  "id": 1,
  "clientId": 1,
  "clientName": "Maria Brown",
  "moment": "2019-06-20T19:53:07Z",
  "status": "WAITING_PAYMENT",
  "total": 1287.9,
  "subtotal": 1431,
  "desconto": 143.1,
  "items": [
    {
      "productName": "The Lord of the Rings",
      "quantity": 2,
      "price": 90.5,
      "subTotal": 181,
      "discount": 0
    }
  ]
}
## Tratamento de erros

A API possui tratamento global com respostas padronizadas:

{
  "timestamp": "2026-03-23T19:00:00Z",
  "status": 400,
  "path": "/users",
  "error": "Erro de validação",
  "message": "Campos inválidos"
}
##  Banco de dados

Banco utilizado: H2 (memória)

Console
http://localhost:8080/h2-console
Configuração
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password:


## Como executar

Clonar repositório
git clone https://github.com/Cleitondevjunior/springboot-ecommerce-api.git
Executar aplicação

Windows:

mvnw.cmd spring-boot:run

Linux/Mac:

./mvnw spring-boot:run

##  Dados de teste

O projeto já carrega dados automaticamente via:

TestConfig (@Profile("test"))


##  Melhorias futuras

autenticação com JWT
Swagger (documentação)
paginação
PostgreSQL
Docker
testes automatizados


##  Autor

Cleiton Andrade

Projeto feito em 23/03/2026

Desenvolvedor Backend Java
Foco em Spring Boot e APIs REST

##  Destaques

arquitetura limpa
uso de DTOs e mappers
regras de negócio implementadas
tratamento global de erros
segurança configurada
pronto para evolução

##  Status

Projeto em evolução com base sólida para aplicações reais.