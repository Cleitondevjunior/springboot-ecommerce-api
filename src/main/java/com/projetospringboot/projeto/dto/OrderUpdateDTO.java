package com.projetospringboot.projeto.dto;

import java.io.Serializable;
import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para atualização de pedidos.
 *
 * Permite atualização parcial dos dados do pedido.
 */
public class OrderUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Momento do pedido.
     * Opcional (mas se informado, deve ser válido).
     */
    private Instant moment;

    /**
     * Status do pedido.
     * Representado como String (ex: "PAID", "SHIPPED").
     */
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status;

    /**
     * ID do cliente associado ao pedido.
     */
    private Long clientId;

    /**
     * Construtor padrão.
     */
    public OrderUpdateDTO() {
    }

    /**
     * Construtor com parâmetros.
     */
    public OrderUpdateDTO(Instant moment, String status, Long clientId) {
        this.moment = moment;
        this.status = status;
        this.clientId = clientId;
    }

    // ===================== GETTERS =====================

    public Instant getMoment() {
        return moment;
    }

    public String getStatus() {
        return status;
    }

    public Long getClientId() {
        return clientId;
    }

    // ===================== SETTERS =====================

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}