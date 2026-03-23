package com.projetospringboot.projeto.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.User;

//  Interface responsável por acesso a dados da entidade Order
// JpaRepository já fornece CRUD completo automaticamente
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    //  Buscar pedidos por cliente
    List<Order> findByClient(User client);

    //  Buscar pedidos por status
    List<Order> findByOrderStatus(Integer status);

    //  Buscar pedidos por período
    List<Order> findByMomentBetween(Instant start, Instant end);

    //  Buscar pedidos por cliente + período
    List<Order> findByClientAndMomentBetween(User client, Instant start, Instant end);
}