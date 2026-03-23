package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import com.projetospringboot.projeto.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    // ===================== CONSTRUTORES =====================

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // construtor que recebe a entidade
    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    // ===================== GETTERS E SETTERS =====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ===================== toString =====================

    @Override
    public String toString() {
        return "CategoryDTO [id=" + id + ", name=" + name + "]";
    }
}