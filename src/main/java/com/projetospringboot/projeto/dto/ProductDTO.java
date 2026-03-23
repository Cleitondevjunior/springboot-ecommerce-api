package com.projetospringboot.projeto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imgUrl;

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