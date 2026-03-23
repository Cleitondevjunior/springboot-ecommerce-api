package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetospringboot.projeto.entity.enums.OrderStatus;

import jakarta.persistence.*;

/**
 * Entidade que representa um pedido do sistema.
 * 
 * Contém relacionamento com usuário, itens e pagamento,
 * além de regras de negócio como cálculo de total e desconto.
 */
@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Valor mínimo para aplicar desconto no pedido.
     */
    private static final BigDecimal DISCOUNT_LIMIT = new BigDecimal("100.00");

    /**
     * Percentual de desconto aplicado quando o limite é atingido.
     */
    private static final BigDecimal DISCOUNT_PERCENT = new BigDecimal("0.10");

    /**
     * Identificador único do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Momento em que o pedido foi realizado.
     */
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        timezone = "GMT"
    )
    private Instant moment;

    /**
     * Status do pedido armazenado como Integer (código do enum).
     */
    private Integer orderStatus;

    /**
     * Cliente responsável pelo pedido.
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    /**
     * Itens do pedido.
     */
    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    /**
     * Pagamento associado ao pedido.
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public Order() {}

    /**
     * Construtor com parâmetros.
     */
    public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
        this.id = id;
        this.moment = moment;
        setOrderStatus(orderStatus);
        this.client = client;
    }

    // ===================== GETTERS =====================

    public Long getId() { return id; }

    public Instant getMoment() { return moment; }

    /**
     * Retorna o status do pedido como enum.
     */
    public OrderStatus getOrderStatus() {
        return orderStatus == null ? null : OrderStatus.valueOf(orderStatus);
    }

    public User getClient() { return client; }

    public Set<OrderItem> getItems() { return items; }

    public Payment getPayment() { return payment; }

    // ===================== SETTERS =====================

    public void setId(Long id) { this.id = id; }

    public void setMoment(Instant moment) { this.moment = moment; }

    /**
     * Define o status do pedido convertendo o enum para código.
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != null) {
            this.orderStatus = orderStatus.getCode();
        }
    }

    public void setClient(User client) { this.client = client; }

    /**
     * Define o pagamento e mantém o relacionamento bidirecional.
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setOrder(this);
        }
    }

    // ===================== AUXILIAR =====================

    /**
     * Padroniza valores monetários para 2 casas decimais.
     */
    private BigDecimal scale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    /**
     * Calcula o total dos itens do pedido.
     */
    public BigDecimal getItemsTotal() {

        BigDecimal sum = BigDecimal.ZERO;

        for (OrderItem item : items) {
            sum = sum.add(item.getSubTotal());
        }

        return scale(sum);
    }

    /**
     * Calcula o desconto do pedido.
     * 
     * Regra:
     * - Se o total for maior que 100, aplica 10% de desconto.
     */
    @JsonIgnore
    public BigDecimal getOrderDiscount() {

        BigDecimal total = getItemsTotal();

        if (total.compareTo(DISCOUNT_LIMIT) > 0) {
            return scale(total.multiply(DISCOUNT_PERCENT));
        }

        return BigDecimal.ZERO;
    }

    /**
     * Calcula o total final do pedido.
     */
    public BigDecimal getTotal() {

        BigDecimal result = getItemsTotal().subtract(getOrderDiscount());

        return scale(result);
    }

    // ===================== EQUALS / HASHCODE =====================

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Order)) return false;
        Order other = (Order) obj;
        return Objects.equals(id, other.id);
    }
}