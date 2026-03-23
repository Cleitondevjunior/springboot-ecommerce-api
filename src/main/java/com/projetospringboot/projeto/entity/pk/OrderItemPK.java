package com.projetospringboot.projeto.entity.pk;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.Product;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Classe que representa a chave composta da entidade OrderItem.
 *
 * A chave é formada por:
 * - pedido (Order)
 * - produto (Product)
 *
 * Essa abordagem permite que um pedido tenha múltiplos produtos,
 * sem duplicar o mesmo produto dentro do mesmo pedido.
 */
@Embeddable
public class OrderItemPK implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Referência ao pedido.
     */
    @JsonIgnore // evita loop na serialização
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Referência ao produto.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // ===================== CONSTRUTORES =====================

    public OrderItemPK() {
    }

    public OrderItemPK(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    // ===================== GETTERS =====================

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    // ===================== SETTERS =====================

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // ===================== EQUALS E HASHCODE =====================

    /**
     * Utiliza apenas os IDs de Order e Product para comparação.
     *
     * Isso evita problemas com proxies do Hibernate e garante
     * consistência no contexto de persistência.
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                order != null ? order.getId() : null,
                product != null ? product.getId() : null
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof OrderItemPK)) return false;

        OrderItemPK other = (OrderItemPK) obj;

        return Objects.equals(
                order != null ? order.getId() : null,
                other.order != null ? other.order.getId() : null
        ) &&
        Objects.equals(
                product != null ? product.getId() : null,
                other.product != null ? other.product.getId() : null
        );
    }
}