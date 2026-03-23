package com.projetospringboot.projeto.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;

    private Long clientId;
    private String clientName;

    private Instant moment;
    private String status;

    private BigDecimal total;

    //  ADICIONAR
    private BigDecimal subtotal;
    private BigDecimal desconto;

    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long clientId, String clientName,
                    Instant moment, String status, BigDecimal total,
                    BigDecimal subtotal, BigDecimal desconto,
                    List<OrderItemDTO> items) {

        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.moment = moment;
        this.status = status;
        this.total = total;
        this.subtotal = subtotal;
        this.desconto = desconto;
        this.items = items;
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
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}