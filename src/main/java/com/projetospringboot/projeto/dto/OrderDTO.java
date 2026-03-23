package com.projetospringboot.projeto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO responsável por transportar os dados do pedido (Order).
 *
 * Utilizado na camada de resposta da API, evitando expor
 * diretamente a entidade e garantindo controle sobre os dados retornados.
 */
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador do pedido.
     */
    private Long id;

    /**
     * Dados do cliente.
     */
    private Long clientId;
    private String clientName;

    /**
     * Data/hora do pedido.
     */
    private Instant moment;

    /**
     * Status do pedido (representado como String).
     */
    private String status;

    /**
     * Total final do pedido.
     */
    private BigDecimal total;

    /**
     * Soma dos itens antes do desconto.
     */
    private BigDecimal subtotal;

    /**
     * Valor total do desconto aplicado.
     */
    private BigDecimal desconto;

    /**
     * Lista de itens do pedido.
     */
    private List<OrderItemDTO> items = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public OrderDTO() {
    }

    /**
     * Construtor completo.
     */
    public OrderDTO(Long id,
                    Long clientId,
                    String clientName,
                    Instant moment,
                    String status,
                    BigDecimal total,
                    BigDecimal subtotal,
                    BigDecimal desconto,
                    List<OrderItemDTO> items) {

        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.moment = moment;
        this.status = status;
        this.total = total;
        this.subtotal = subtotal;
        this.desconto = desconto;
        setItems(items); // evita null
    }

    // ===================== GETTERS =====================

    public Long getId() { return id; }

    public Long getClientId() { return clientId; }

    public String getClientName() { return clientName; }

    public Instant getMoment() { return moment; }

    public String getStatus() { return status; }

    public BigDecimal getTotal() { return total; }

    public BigDecimal getSubtotal() { return subtotal; }

    public BigDecimal getDesconto() { return desconto; }

    public List<OrderItemDTO> getItems() { return items; }

    // ===================== SETTERS =====================

    public void setId(Long id) { this.id = id; }

    public void setClientId(Long clientId) { this.clientId = clientId; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    public void setMoment(Instant moment) { this.moment = moment; }

    public void setStatus(String status) { this.status = status; }

    public void setTotal(BigDecimal total) { this.total = total; }

    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }

    /**
     * Garante que a lista de itens nunca seja null.
     */
    public void setItems(List<OrderItemDTO> items) {
        this.items = (items != null) ? items : new ArrayList<>();
    }
}