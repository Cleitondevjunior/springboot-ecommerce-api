package com.projetospringboot.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidade que representa um produto do sistema.
 *
 * Contém informações básicas do produto, relacionamentos com categorias
 * e itens de pedido, além de pequenas regras de negócio.
 */
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador do produto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do produto.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Descrição detalhada do produto.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Preço do produto com precisão monetária.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * URL da imagem do produto.
     */
    private String imgUrl;

    // ===================== RELACIONAMENTOS =====================

    /**
     * Categorias associadas ao produto.
     */
    @ManyToMany
    @JoinTable(
        name = "tb_product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    /**
     * Itens de pedido que referenciam este produto.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "id.product")
    private Set<OrderItem> items = new HashSet<>();

    // ===================== CONSTRUTORES =====================

    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // ===================== GETTERS =====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Retorna os pedidos relacionados ao produto.
     *
     * Método derivado a partir dos itens do pedido.
     */
    @JsonIgnore
    public Set<Order> getOrders() {

        Set<Order> set = new HashSet<>();

        for (OrderItem item : items) {
            set.add(item.getOrder());
        }

        return set;
    }

    // ===================== SETTERS =====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // ===================== REGRAS DE NEGÓCIO =====================

    /**
     * Calcula o preço com desconto aplicado.
     *
     * @param percentage percentual de desconto (ex: 0.10 = 10%)
     * @return preço com desconto
     */
    public BigDecimal getPriceWithDiscount(BigDecimal percentage) {

        if (price == null || percentage == null) {
            return price;
        }

        BigDecimal discount = price.multiply(percentage);

        return price.subtract(discount);
    }

    /**
     * Validação simples de preço.
     *
     * @return true se o preço for válido
     */
    public boolean isValidPrice() {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    // ===================== EQUALS / HASHCODE =====================

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return Objects.equals(id, other.id);
    }
}