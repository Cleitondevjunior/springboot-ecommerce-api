package com.projetospringboot.projeto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.OrderCreateDTO;
import com.projetospringboot.projeto.dto.OrderDTO;
import com.projetospringboot.projeto.dto.OrderItemDTO;
import com.projetospringboot.projeto.dto.OrderUpdateDTO;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.OrderItem;
import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.entity.enums.OrderStatus;

/**
 * Classe responsável pelas conversões entre a entidade Order
 * e os DTOs utilizados pela aplicação.
 */
@Component
public class OrderMapper {

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte uma entidade Order para OrderDTO.
     *
     * @param entity entidade do pedido
     * @return DTO de resposta
     */
    public OrderDTO toDTO(Order entity) {

        if (entity == null) {
            return null;
        }

        Long clientId = null;
        String clientName = null;

        if (entity.getClient() != null) {
            clientId = entity.getClient().getId();
            clientName = entity.getClient().getName();
        }

        List<OrderItemDTO> items = entity.getItems()
                .stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());

        OrderDTO dto = new OrderDTO();

        dto.setId(entity.getId());
        dto.setClientId(clientId);
        dto.setClientName(clientName);
        dto.setMoment(entity.getMoment());
        dto.setStatus(entity.getOrderStatus() != null ? entity.getOrderStatus().name() : null);

        // Campos calculados do pedido
        dto.setSubtotal(entity.getItemsTotal());
        dto.setDesconto(entity.getOrderDiscount());
        dto.setTotal(entity.getTotal());

        dto.setItems(items);

        return dto;
    }

    // ===================== ITEM -> DTO =====================

    /**
     * Converte um item da entidade para OrderItemDTO.
     *
     * @param item item do pedido
     * @return DTO do item
     */
    private OrderItemDTO toItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubTotal(),
                item.getDiscount()
        );
    }

    // ===================== CREATE DTO -> ENTITY =====================

    /**
     * Converte OrderCreateDTO para entidade Order.
     *
     * Neste ponto, o cliente é associado apenas pelo ID.
     * A validação mais completa deve ficar na camada de service.
     *
     * @param dto dados de criação do pedido
     * @return entidade Order
     */
    public Order toEntity(OrderCreateDTO dto) {

        if (dto == null) {
            return null;
        }

        Order entity = new Order();

        entity.setMoment(dto.getMoment());

        /**
         * Observação:
         * o valor recebido em dto.getStatus() deve ser exatamente
         * o nome do enum, por exemplo: "PAID", "DELIVERED", etc.
         */
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            entity.setOrderStatus(OrderStatus.valueOf(dto.getStatus()));
        }

        if (dto.getClientId() != null) {
            User user = new User();
            user.setId(dto.getClientId());
            entity.setClient(user);
        }

        return entity;
    }

    // ===================== UPDATE DTO -> ENTITY =====================

    /**
     * Atualiza a entidade Order com base no OrderUpdateDTO.
     *
     * Apenas os campos não nulos são alterados.
     *
     * @param entity entidade existente
     * @param dto dados de atualização
     */
    public void updateEntity(Order entity, OrderUpdateDTO dto) {

        if (entity == null || dto == null) {
            return;
        }

        if (dto.getMoment() != null) {
            entity.setMoment(dto.getMoment());
        }

        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            entity.setOrderStatus(OrderStatus.valueOf(dto.getStatus()));
        }

        if (dto.getClientId() != null) {
            User user = new User();
            user.setId(dto.getClientId());
            entity.setClient(user);
        }
    }
}