package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //  relacionamento muitos-para-muitos com Product
    @JsonIgnore
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

    //  exemplo útil (validação simples)
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