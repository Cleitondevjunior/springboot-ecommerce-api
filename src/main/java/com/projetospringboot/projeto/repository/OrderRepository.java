package com.projetospringboot.projeto.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.User;

/**
 * Interface responsável pelo acesso a dados da entidade Order.
 *
 * Ao estender JpaRepository, já herdamos automaticamente:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 *
 * Além disso, podemos criar consultas automaticamente
 * apenas definindo o nome dos métodos.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    /**
     * Busca todos os pedidos de um cliente específico.
     *
     * @param client usuário (cliente)
     * @return lista de pedidos do cliente
     */
    List<Order> findByClient(User client);

    /**
     * Busca pedidos por status.
     *
     * O status é armazenado como Integer (código do enum).
     *
     * @param status código do status do pedido
     * @return lista de pedidos com o status informado
     */
    List<Order> findByOrderStatus(Integer status);

    /**
     * Busca pedidos dentro de um intervalo de datas.
     *
     * @param start data inicial
     * @param end data final
     * @return lista de pedidos no período informado
     */
    List<Order> findByMomentBetween(Instant start, Instant end);

    /**
     * Busca pedidos de um cliente dentro de um intervalo de datas.
     *
     * Combina filtros de cliente + período.
     *
     * @param client usuário (cliente)
     * @param start data inicial
     * @param end data final
     * @return lista de pedidos filtrados
     */
    List<Order> findByClientAndMomentBetween(User client, Instant start, Instant end);
}