package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO utilizado para criação de itens do pedido.
 *
 * Representa cada produto dentro de um pedido,
 * contendo apenas os dados necessários para criação.
 */
public class OrderItemCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID do produto associado ao item.
     */
    @NotNull(message = "O produto é obrigatório")
    private Long productId;

    /**
     * Quantidade do produto no pedido.
     */
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private Integer quantity;

    /**
     * Construtor padrão.
     */
    public OrderItemCreateDTO() {
    }

    /**
     * Construtor com parâmetros.
     */
    public OrderItemCreateDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // ===================== GETTERS =====================

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // ===================== SETTERS =====================

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}