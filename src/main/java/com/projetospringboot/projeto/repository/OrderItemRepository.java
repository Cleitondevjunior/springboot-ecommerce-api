package com.projetospringboot.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.OrderItem;
import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.entity.pk.OrderItemPK;

//  Interface responsável por acesso a dados da entidade OrderItem
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    //  Buscar itens por pedido
    List<OrderItem> findByIdOrder(Order order);

    //  Buscar itens por produto
    List<OrderItem> findByIdProduct(Product product);

    //  Buscar itens por pedido e produto
    List<OrderItem> findByIdOrderAndIdProduct(Order order, Product product);
}