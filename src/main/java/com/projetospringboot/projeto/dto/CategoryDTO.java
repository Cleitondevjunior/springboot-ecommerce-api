package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import com.projetospringboot.projeto.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO responsável por transportar dados da entidade Category.
 *
 * Utilizado para entrada e saída da API, evitando expor diretamente a entidade.
 */
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador da categoria.
     */
    private Long id;

    /**
     * Nome da categoria.
     */
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    // ===================== CONSTRUTORES =====================

    /**
     * Construtor padrão.
     */
    public CategoryDTO() {
    }

    /**
     * Construtor com parâmetros.
     */
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Construtor que converte Entity → DTO.
     *
     * @param entity entidade Category
     */
    public CategoryDTO(Category entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
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