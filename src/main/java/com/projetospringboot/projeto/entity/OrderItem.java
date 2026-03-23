package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projetospringboot.projeto.entity.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Integer quantity;
    private BigDecimal price;

    // ===================== CONSTANTES =====================
    private static final BigDecimal DISCOUNT_5 = new BigDecimal("0.05");
    private static final BigDecimal DISCOUNT_10 = new BigDecimal("0.10");

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    // ===================== GETTERS =====================

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    // ===================== SETTERS =====================

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // ===================== MÉTODO AUXILIAR =====================
    private BigDecimal scale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    //  Subtotal bruto (sem desconto)
    @JsonProperty("rawSubTotal")
    public BigDecimal getRawSubTotal() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return scale(price.multiply(BigDecimal.valueOf(quantity)));
    }

    //  Desconto por item
    @JsonProperty("discount")
    public BigDecimal getDiscount() {

        if (quantity == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal raw = getRawSubTotal();
        BigDecimal discount;

        if (quantity >= 10) {
            discount = raw.multiply(DISCOUNT_10);
        } else if (quantity >= 5) {
            discount = raw.multiply(DISCOUNT_5);
        } else {
            discount = BigDecimal.ZERO;
        }

        return scale(discount);
    }

    //  Subtotal final (com desconto)
    @JsonProperty("subTotal")
    public BigDecimal getSubTotal() {

        BigDecimal raw = getRawSubTotal();
        BigDecimal discount = getDiscount();

        return scale(raw.subtract(discount));
    }

    // ===================== EQUALS / HASHCODE =====================

    @Override
    public int hashCode() {
        return Objects.hash(
            id.getOrder() != null ? id.getOrder().getId() : null,
            id.getProduct() != null ? id.getProduct().getId() : null
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof OrderItem)) return false;

        OrderItem other = (OrderItem) obj;

        return Objects.equals(
                id.getOrder() != null ? id.getOrder().getId() : null,
                other.id.getOrder() != null ? other.id.getOrder().getId() : null
        ) &&
        Objects.equals(
                id.getProduct() != null ? id.getProduct().getId() : null,
                other.id.getProduct() != null ? other.id.getProduct().getId() : null
        );
    }
}