package com.projetospringboot.projeto.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Product;

//  Interface responsável por acesso a dados da entidade Product
// JpaRepository já fornece CRUD completo automaticamente
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    //  Buscar produtos pelo nome (like)
    List<Product> findByNameContainingIgnoreCase(String name);

    //  Buscar produtos por faixa de preço
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    //  Buscar produtos acima de um valor
    List<Product> findByPriceGreaterThan(BigDecimal value);

    //  Buscar produtos abaixo de um valor
    List<Product> findByPriceLessThan(BigDecimal value);
}