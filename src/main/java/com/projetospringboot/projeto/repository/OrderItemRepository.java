package com.projetospringboot.projeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.OrderItem;
import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.entity.pk.OrderItemPK;

/**
 * Interface responsável pelo acesso a dados da entidade OrderItem.
 *
 * Trabalha com chave composta (OrderItemPK), permitindo consultas
 * baseadas no pedido e no produto associados ao item.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    /**
     * Busca todos os itens de um pedido específico.
     *
     * @param order pedido a ser consultado
     * @return lista de itens do pedido
     */
    List<OrderItem> findByIdOrder(Order order);

    /**
     * Busca todos os itens associados a um produto específico.
     *
     * @param product produto a ser consultado
     * @return lista de itens que utilizam o produto informado
     */
    List<OrderItem> findByIdProduct(Product product);

    /**
     * Busca um item específico pelo par pedido + produto.
     *
     * Como a combinação entre pedido e produto normalmente identifica
     * um único item, o retorno mais adequado é Optional<OrderItem>.
     *
     * @param order pedido
     * @param product produto
     * @return item encontrado, se existir
     */
    Optional<OrderItem> findByIdOrderAndIdProduct(Order order, Product product);
}