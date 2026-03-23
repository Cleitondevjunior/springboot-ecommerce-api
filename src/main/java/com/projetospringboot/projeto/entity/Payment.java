package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        timezone = "GMT")
    private Instant moment;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

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

    //  verifica se pagamento já foi realizado
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