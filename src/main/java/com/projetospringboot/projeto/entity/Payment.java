package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidade que representa o pagamento de um pedido.
 *
 * Relacionamento:
 * - Um Payment está associado a um único Order.
 * - Compartilha o mesmo ID do pedido (@MapsId).
 */
@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador do pagamento.
     * Compartilha o mesmo ID do pedido.
     */
    @Id
    private Long id;

    /**
     * Momento em que o pagamento foi realizado.
     */
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        timezone = "GMT"
    )
    private Instant moment;

    /**
     * Pedido associado ao pagamento.
     */
    @JsonIgnore // evita loop na serialização JSON
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    // ===================== CONSTRUTORES =====================

    public Payment() {
    }

    public Payment(Long id, Instant moment, Order order) {
        this.id = id;
        this.moment = moment;
        this.order = order;
    }

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public Order getOrder() {
        return order;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    /**
     * Verifica se o pagamento já foi realizado.
     *
     * @return true se o pagamento foi efetuado
     */
    public boolean isPaid() {
        return this.moment != null;
    }

    // ===================== EQUALS / HASHCODE =====================

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Payment)) return false;
        Payment other = (Payment) obj;
        return Objects.equals(id, other.id);
    }
}