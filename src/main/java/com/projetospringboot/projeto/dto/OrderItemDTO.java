package com.projetospringboot.projeto.dto;

import java.math.BigDecimal;

public class OrderItemDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal price;

    private BigDecimal subTotal;
    private BigDecimal discount;

    public OrderItemDTO() {
    }

    public OrderItemDTO(String productName, Integer quantity, BigDecimal price,
                        BigDecimal subTotal, BigDecimal discount) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
        this.discount = discount;
    }

    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getSubTotal() { return subTotal; }
    public BigDecimal getDiscount() { return discount; }

    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
}