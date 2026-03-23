package com.projetospringboot.projeto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projetospringboot.projeto.dto.OrderDTO;
import com.projetospringboot.projeto.dto.OrderItemDTO;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.OrderItem;
import com.projetospringboot.projeto.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Order.
 *
 * Aqui centralizamos:
 * - persistência de pedidos
 * - validações de existência
 * - conversão de entidade para DTO
 */
@Service
public class OrderService {

    /**
     * Repositório responsável pelo acesso a dados da entidade Order.
     */
    private final OrderRepository orderRepository;

    /**
     * Injeção de dependência por construtor.
     *
     * @param orderRepository repositório de pedidos
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ===================== GET ALL =====================

    /**
     * Busca todos os pedidos cadastrados.
     *
     * @return lista de pedidos convertidos para DTO
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== GET BY ID =====================

    /**
     * Busca um pedido pelo ID.
     *
     * @param id identificador do pedido
     * @return pedido convertido para DTO
     * @throws EntityNotFoundException se o pedido não existir
     */
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = findEntityById(id);
        return toDTO(order);
    }

    // ===================== INSERT =====================

    /**
     * Insere um novo pedido no banco.
     *
     * @param obj pedido a ser salvo
     * @return pedido salvo convertido para DTO
     */
    @Transactional
    public OrderDTO insert(Order obj) {
        Order saved = orderRepository.save(obj);
        return toDTO(saved);
    }

    // ===================== DELETE =====================

    /**
     * Remove um pedido pelo ID.
     *
     * @param id identificador do pedido
     * @throws EntityNotFoundException se o pedido não existir
     */
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado. ID: " + id);
        }

        orderRepository.deleteById(id);
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza um pedido existente.
     *
     * @param id identificador do pedido
     * @param obj novos dados do pedido
     * @return pedido atualizado convertido para DTO
     * @throws EntityNotFoundException se o pedido não existir
     */
    @Transactional
    public OrderDTO update(Long id, Order obj) {

        Order entity = findEntityById(id);

        updateData(entity, obj);

        Order updated = orderRepository.save(entity);

        return toDTO(updated);
    }

    // ===================== BUSCA AUXILIAR =====================

    /**
     * Busca a entidade Order pelo ID.
     *
     * Método auxiliar para evitar repetição de código.
     *
     * @param id identificador do pedido
     * @return entidade Order encontrada
     * @throws EntityNotFoundException se o pedido não existir
     */
    private Order findEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado. ID: " + id));
    }

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte uma entidade Order para OrderDTO.
     *
     * @param order entidade pedido
     * @return DTO do pedido
     */
    private OrderDTO toDTO(Order order) {

        // Converte os itens do pedido para DTO
        List<OrderItemDTO> items = order.getItems()
                .stream()
                .map(this::toOrderItemDTO)
                .collect(Collectors.toList());

        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setMoment(order.getMoment());

        // Preenche dados do cliente, se existir
        if (order.getClient() != null) {
            dto.setClientId(order.getClient().getId());
            dto.setClientName(order.getClient().getName());
        }

        // Preenche status, se existir
        if (order.getOrderStatus() != null) {
            dto.setStatus(order.getOrderStatus().name());
        }

        // Campos calculados do pedido
        dto.setSubtotal(order.getItemsTotal());
        dto.setDesconto(order.getOrderDiscount());
        dto.setTotal(order.getTotal());

        // Lista de itens convertidos
        dto.setItems(items);

        return dto;
    }

    // ===================== ITEM -> DTO =====================

    /**
     * Converte um OrderItem para OrderItemDTO.
     *
     * @param item item do pedido
     * @return DTO do item
     */
    private OrderItemDTO toOrderItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubTotal(),
                item.getDiscount()
        );
    }

    // ===================== UPDATE AUX =====================

    /**
     * Atualiza os campos permitidos da entidade Order.
     *
     * @param entity entidade original
     * @param obj novos dados
     */
    private void updateData(Order entity, Order obj) {
        entity.setMoment(obj.getMoment());
        entity.setOrderStatus(obj.getOrderStatus());
        entity.setClient(obj.getClient());
    }
}