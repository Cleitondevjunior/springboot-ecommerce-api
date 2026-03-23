package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidade que representa uma categoria de produtos.
 *
 * Uma categoria pode estar associada a vários produtos,
 * e um produto pode pertencer a várias categorias.
 */
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador da categoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da categoria.
     * Deve ser único no sistema.
     */
    @Column(nullable = false, unique = true)
    private String name;

    // ===================== RELACIONAMENTOS =====================

    /**
     * Relação muitos-para-muitos com Product.
     *
     * mappedBy indica que o controle da relação está na entidade Product.
     */
    @JsonIgnore // evita loop na serialização JSON
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    // ===================== CONSTRUTORES =====================

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    /**
     * Validação simples para o nome da categoria.
     *
     * @return true se o nome for válido
     */
    public boolean isValidName() {
        return name != null && !name.trim().isEmpty();
    }

    // ===================== EQUALS / HASHCODE =====================

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Category)) return false;
        Category other = (Category) obj;
        return Objects.equals(id, other.id);
    }
}