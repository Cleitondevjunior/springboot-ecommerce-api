package com.projetospringboot.projeto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO responsável por transportar os dados do produto.
 *
 * Utilizado tanto para entrada quanto saída da API.
 */
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador do produto.
     */
    private Long id;

    /**
     * Nome do produto.
     */
    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String name;

    /**
     * Descrição do produto.
     */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String description;

    /**
     * Preço do produto.
     */
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal price;

    /**
     * URL da imagem do produto.
     */
    private String imgUrl;

    // ===================== CONSTRUTORES =====================

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, BigDecimal price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}