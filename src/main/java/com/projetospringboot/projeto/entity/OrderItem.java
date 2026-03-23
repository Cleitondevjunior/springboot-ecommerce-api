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

/**
 * Entidade que representa um item de pedido.
 *
 * Essa entidade faz a ligação entre Order e Product,
 * armazenando também quantidade, preço e regras de desconto.
 */
@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Chave composta formada por:
     * - pedido
     * - produto
     */
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    /**
     * Quantidade do produto no pedido.
     */
    private Integer quantity;

    /**
     * Preço unitário do produto no momento da compra.
     */
    private BigDecimal price;

    // ===================== CONSTANTES =====================

    /**
     * Desconto de 5% para compras a partir de 5 unidades.
     */
    private static final BigDecimal DISCOUNT_5 = new BigDecimal("0.05");

    /**
     * Desconto de 10% para compras a partir de 10 unidades.
     */
    private static final BigDecimal DISCOUNT_10 = new BigDecimal("0.10");

    // ===================== CONSTRUTORES =====================

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    // ===================== GETTERS =====================

    /**
     * Retorna o pedido associado ao item.
     *
     * É ignorado no JSON para evitar loop de serialização.
     */
    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    /**
     * Retorna o produto associado ao item.
     */
    public Product getProduct() {
        return id.getProduct();
    }

    /**
     * Retorna a quantidade comprada.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Retorna o preço unitário do item.
     */
    public BigDecimal getPrice() {
        return price;
    }

    // ===================== SETTERS =====================

    /**
     * Define o pedido associado ao item.
     */
    public void setOrder(Order order) {
        id.setOrder(order);
    }

    /**
     * Define o produto associado ao item.
     */
    public void setProduct(Product product) {
        id.setProduct(product);
    }

    /**
     * Define a quantidade do item.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Define o preço unitário do item.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // ===================== MÉTODO AUXILIAR =====================

    /**
     * Padroniza valores monetários com 2 casas decimais.
     *
     * Usa HALF_EVEN, adequado para operações financeiras.
     */
    private BigDecimal scale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    /**
     * Calcula o subtotal bruto do item, sem desconto.
     *
     * Fórmula:
     * preço unitário × quantidade
     *
     * @return subtotal bruto
     */
    @JsonProperty("rawSubTotal")
    public BigDecimal getRawSubTotal() {

        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return scale(price.multiply(BigDecimal.valueOf(quantity)));
    }

    /**
     * Calcula o desconto aplicado ao item com base na quantidade.
     *
     * Regras:
     * - 10 ou mais unidades → 10% de desconto
     * - 5 a 9 unidades → 5% de desconto
     * - abaixo de 5 unidades → sem desconto
     *
     * @return valor do desconto
     */
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

    /**
     * Calcula o subtotal final do item já com desconto aplicado.
     *
     * Fórmula:
     * subtotal bruto - desconto
     *
     * @return subtotal final
     */
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