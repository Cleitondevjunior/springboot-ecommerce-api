package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidade que representa o usuário do sistema.
 * Mapeada para a tabela "tb_user" no banco de dados.
 */
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    // Controle de versão da serialização
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do usuário (chave primária).
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário.
     */
    private String name;

    /**
     * Email do usuário.
     * Deve ser único e não pode ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Telefone do usuário.
     */
    private String phone;

    /**
     * Senha do usuário.
     * Não deve ser exposta em respostas da API.
     */
    @JsonIgnore
    private String password;

    /**
     * Lista de pedidos associados ao usuário.
     * Relacionamento OneToMany (um usuário pode ter vários pedidos).
     * O mappedBy indica que a chave está na entidade Order (campo "client").
     */
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    /**
     * Construtor padrão (obrigatório para o JPA).
     */
    public User() {
    }

    /**
     * Construtor com parâmetros.
     * Utilizado para criar objetos completos da entidade.
     */
    public User(Long id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // ===================== GETTERS =====================

    /**
     * Retorna o ID do usuário.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna o nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna o email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna o telefone do usuário.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Retorna a senha do usuário.
     * OBS: Mesmo com getter, ela não será exibida no JSON por causa do @JsonIgnore.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retorna a lista de pedidos do usuário.
     */
    public List<Order> getOrders() {
        return orders;
    }

    // ===================== SETTERS =====================

    /**
     * Define o ID do usuário.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Define o nome do usuário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Define o email do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Define o telefone do usuário.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Define a senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // ===================== EQUALS E HASHCODE =====================

    /**
     * Gera o hashCode baseado no ID.
     * Utilizado para comparação em coleções (Set, Map, etc).
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Compara dois objetos User.
     * Dois usuários são considerados iguais se tiverem o mesmo ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // mesma referência
        if (!(obj instanceof User)) return false; // tipo diferente
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }
}