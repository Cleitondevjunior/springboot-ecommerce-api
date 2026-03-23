package com.projetospringboot.projeto.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Product;

/**
 * Interface responsável pelo acesso a dados da entidade Product.
 *
 * Ao estender JpaRepository, já temos:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 *
 * Além disso, utilizamos consultas derivadas pelo nome dos métodos.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    /**
     * Busca produtos pelo nome (ignorando maiúsculas/minúsculas).
     *
     * Exemplo: "notebook" → retorna "Notebook Dell", "Notebook Acer"
     *
     * @param name parte do nome do produto
     * @return lista de produtos encontrados
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Busca produtos dentro de uma faixa de preço.
     *
     * @param min valor mínimo
     * @param max valor máximo
     * @return lista de produtos na faixa informada
     */
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    /**
     * Busca produtos com preço maior que o valor informado.
     *
     * @param value valor base
     * @return lista de produtos
     */
    List<Product> findByPriceGreaterThan(BigDecimal value);

    /**
     * Busca produtos com preço menor que o valor informado.
     *
     * @param value valor base
     * @return lista de produtos
     */
    List<Product> findByPriceLessThan(BigDecimal value);
}